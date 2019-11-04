package pl.edu.pg.eti.kask.kzawora.housing_community.model;

import lombok.*;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Address;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"realEstates", "manager", "links"})
@ToString(exclude = {"realEstates", "manager", "links"})
@Entity
@Table(name = "housingCommunities")
@NamedQuery(name = HousingCommunity.Queries.FIND_ALL, query = "select hc from HousingCommunity hc")
@NamedQuery(name = HousingCommunity.Queries.COUNT, query = "select count(hc) from HousingCommunity hc")
public class HousingCommunity implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "HousingCommunity.findAll";
        public static final String COUNT = "HousingCommunity.count";
    }

    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    @NotBlank
    @Size(min=1, max=100)
    private String name;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "manager")
    @JsonbTransient
    private Manager manager = new Manager();

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "housingCommunity")
    @Getter
    @Setter
    private List<RealEstate> realEstates = new ArrayList<>();

    @Getter
    @Setter
    private Address address = new Address();

    @Getter
    @Setter
    @NotBlank
    @Pattern(regexp="\\d{9}")
    private String nip;

    public HousingCommunity(HousingCommunity housingCommunity) {
        this.name = housingCommunity.name;
        this.address = housingCommunity.address;
        this.nip = housingCommunity.nip;
    }

    public HousingCommunity(String name, Address address, String nip) {
        this.name = name;
        this.address = address;
        this.nip = nip;
    }

    @JsonbProperty("_links")
    @Transient
    @Getter
    @Setter
    private Map<String, Link> links = new HashMap<>();
}
