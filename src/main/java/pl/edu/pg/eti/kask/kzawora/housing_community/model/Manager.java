package pl.edu.pg.eti.kask.kzawora.housing_community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Manager implements Serializable {

    private int id;
    private String name;
    private String email;
    private String nip;

    public Manager(Manager manager) {
        this.id = manager.id;
        this.name = manager.name;
        this.email = manager.email;
        this.nip = manager.nip;
    }

}
