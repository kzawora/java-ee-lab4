package pl.edu.pg.eti.kask.kzawora.user.model;

import lombok.*;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"realEstates", "links"})
@ToString(exclude = {"realEstates", "links"})
@Entity
@Table(name = "users")
@NamedQuery(name = User.Queries.FIND_ALL, query = "select u from User u")
@NamedQuery(name = User.Queries.COUNT, query = "select count(u) from User u")
public class User implements Serializable {
    public static class Queries {
        public static final String FIND_ALL = "User.findAll";
        public static final String COUNT = "User.count";
    }

    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    private PersonalData personalData;

    @Getter
    @Setter
    @NotBlank
    private String email;

    @Getter
    @Setter
    @NotBlank
    private String password;

/*
    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "realEstates_users",
            joinColumns = @JoinColumn(name = "realEstate"),
            inverseJoinColumns = @JoinColumn(name = "user"))
    @Getter
    @Setter
    private List<RealEstate> realEstates = new ArrayList<>();
*/
    public List<RealEstate> getRealEstates() { return null; }

    public User(User user) {
        this.personalData = user.personalData;
        this.email = user.email;
        this.password = user.password;
    }

    public User(PersonalData personalData, String email, String password) {
        this.personalData = personalData;
        this.email = email;
        this.password = password;
    }

    /**
     * HATEOAS links.
     */
    @JsonbProperty("_links")
    @Transient
    @Getter
    @Setter
    private Map<String, Link> links = new HashMap<>();
}
