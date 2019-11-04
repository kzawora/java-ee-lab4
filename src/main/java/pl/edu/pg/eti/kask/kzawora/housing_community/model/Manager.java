package pl.edu.pg.eti.kask.kzawora.housing_community.model;

import lombok.*;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"housingCommunities"})
@ToString(exclude = {"housingCommunities"})
@Entity
@Table(name = "managers")
@NamedQuery(name = Manager.Queries.FIND_ALL, query = "select m from Manager m")
public class Manager implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Manager.findAll";
    }

    @Id
    @GeneratedValue
    @Getter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String nip;

    public Manager(Manager manager) {
        this.name = manager.name;
        this.email = manager.email;
        this.nip = manager.nip;
    }
    public Manager(String name, String email, String nip) {
        this.name = name;
        this.email = email;
        this.nip = nip;
    }

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    @Getter
    @Setter
    private List<HousingCommunity> housingCommunities = new ArrayList<>();
}

