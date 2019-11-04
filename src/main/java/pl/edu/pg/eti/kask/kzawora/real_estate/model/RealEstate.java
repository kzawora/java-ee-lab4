package pl.edu.pg.eti.kask.kzawora.real_estate.model;

import lombok.*;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Address address = new Address();

    @Getter
    @Setter

    @NotNull
    private Double livingSpace;

    @Getter
    @Setter
    @PastOrPresent
    @NotNull
    private LocalDate buildDate;

    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "realEstates_developers",
            joinColumns = @JoinColumn(name = "realEstate"),
            inverseJoinColumns = @JoinColumn(name = "developer"))
    @Getter
    @Setter
    private List<Developer> developers;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "housingCommunity")
    private HousingCommunity housingCommunity = new HousingCommunity();

    /*
        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "realEstates")
        @Getter
        @Setter
        @JsonbTransient
        private List<User> users = new ArrayList<>();
    */
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
