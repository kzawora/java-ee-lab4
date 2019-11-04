package pl.edu.pg.eti.kask.kzawora.housing_community.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Address;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class HousingCommunity implements Serializable {

    private int id;

    private String name;
    @JsonbTransient
    private Manager manager = new Manager();

    private Address address = new Address();

    private String nip;

    public HousingCommunity(HousingCommunity housingCommunity) {
        this.id = housingCommunity.id;
        this.name = housingCommunity.name;
        this.manager = housingCommunity.manager;
        this.address = housingCommunity.address;
        this.nip = housingCommunity.nip;
    }

    public HousingCommunity(int id, String name, Manager manager, Address address, String nip) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.address = address;
        this.nip = nip;
    }

    @JsonbProperty("_links")
    private Map<String, Link> links = new HashMap<>();
}
