package pl.edu.pg.eti.kask.kzawora.real_estate.model;

import lombok.*;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class RealEstate implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "RealEstate.findAll";
        public static final String COUNT = "RealEstate.count";
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
    private double livingSpace;

    @Getter
    @Setter
    private LocalDate buildDate;

    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "realEstates_developers",
            joinColumns = @JoinColumn(name = "realEstate"),
            inverseJoinColumns = @JoinColumn(name = "developer"))
    @Getter
    @Setter
    private List<Developer> developers;

    public List<Developer> getDevelopers() {
        return null;
    }

    //    @Getter
//    @Setter
//    @ManyToOne
//    @JoinColumn(name = "housingCommunity")
//    private HousingCommunity housingCommunity = new HousingCommunity();
    public void setHousingCommunity(HousingCommunity hc) {
    }

    public HousingCommunity getHousingCommunity() {
        return null;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "realEstates")
    @Getter
    @Setter
    @JsonbTransient
    private List<User> users = new ArrayList<>();

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
