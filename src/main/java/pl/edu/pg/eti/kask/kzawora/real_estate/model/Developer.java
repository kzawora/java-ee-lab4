package pl.edu.pg.eti.kask.kzawora.real_estate.model;

import lombok.*;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "realEstates")
@ToString(exclude = "realEstates")
@Entity
@Table(name = "developers")

@NamedQuery(name = Developer.Queries.FIND_ALL, query = "select d from Developer d")
@NamedQuery(name = Developer.Queries.COUNT, query = "select count(d) from Developer d")
@NamedQuery(name = Developer.Queries.FIND_BY_REALESTATE, query = "select d from Developer d where :realEstate member of d.realEstates")
public class Developer implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Developer.findAll";
        public static final String COUNT = "Developer.count";
        public static final String FIND_BY_REALESTATE = "Developer.findByRealEstate";
    }

    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "developers")
    @Getter
    @Setter
    @JsonbTransient
    private List<RealEstate> realEstates = new ArrayList<>();

    public Developer(String name) {
        this.name = name;
    }
}
