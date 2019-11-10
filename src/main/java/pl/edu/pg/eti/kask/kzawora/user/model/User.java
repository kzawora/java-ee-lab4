package pl.edu.pg.eti.kask.kzawora.user.model;

import lombok.*;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

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
    private PersonalData personalData = new PersonalData();

    @Getter
    @Setter
    @NotBlank(message="Email must not be blank.")
    @Size(min=1, max=50, message = "Email must be between 1 and 50 characters long.")
    private String email;

    @Getter
    @Setter
    @NotBlank(message="Password must not be blank.")
    @Size(min=8, max=50, message = "Email must be between 1 and 50 characters long.")
    private String password;


    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "realEstates_users",
            joinColumns = @JoinColumn(name = "realEstate"),
            inverseJoinColumns = @JoinColumn(name = "user"))
    @Getter
    @Setter
    private Set<RealEstate> realEstates = new HashSet<>();

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
