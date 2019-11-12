package pl.edu.pg.eti.kask.kzawora.real_estate.model;

import lombok.*;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users", "developers", "links"})
@ToString(exclude = {"users", "developers", "links"})
@Entity
@Table(name = "realEstates")
@NamedQuery(name = RealEstate.Queries.FIND_ALL, query = "select re from RealEstate re")
@NamedQuery(name = RealEstate.Queries.COUNT, query = "select count(re) from RealEstate re")
@NamedQuery(name = RealEstate.Queries.FIND_BY_DEVELOPER, query = "select re from RealEstate re where :developer member of re.developers")
public class RealEstate implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "RealEstate.findAll";
        public static final String COUNT = "RealEstate.count";
        public static final String FIND_BY_DEVELOPER = "RealEstate.findByDeveloper";

    }

    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address = new Address();

    @Getter
    @Setter

    @NotNull(message = "Living space must not be empty")
    private Double livingSpace;

    @Getter
    @Setter
    @PastOrPresent(message = "Build date must be in the past or present")
    @NotNull(message = "Build date must not be empty")
    private LocalDate buildDate;

    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "realEstates_developers",
            joinColumns = @JoinColumn(name = "realEstate"),
            inverseJoinColumns = @JoinColumn(name = "developer"))
    @Getter
    private Set<Developer> developers;

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = new HashSet<>(developers);
    }

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "housingCommunity")
    private HousingCommunity housingCommunity = new HousingCommunity();


    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "realEstates_developers",
            joinColumns = @JoinColumn(name = "realEstate"),
            inverseJoinColumns = @JoinColumn(name = "developer"))
    @Getter
    private Set<User> users = new HashSet<>();


    public RealEstate(RealEstate realEstate) {
        this.address = realEstate.address;
        this.livingSpace = realEstate.livingSpace;
        this.buildDate = realEstate.buildDate;
    }

    public RealEstate(Address address, double livingSpace, LocalDate buildDate, List<Developer> developers, HousingCommunity housingCommunity) {
        this.address = address;
        this.livingSpace = livingSpace;
        this.buildDate = buildDate;
    }

    /**
     * HATEOAS links.
     */
    @JsonbProperty("_links")
    @Transient
    @Getter
    private Map<String, Link> links = new HashMap<>();

}
