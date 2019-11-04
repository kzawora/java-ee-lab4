package pl.edu.pg.eti.kask.kzawora.real_estate.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Named
@ViewScoped
public class RealEstateEdit implements Serializable {

    private RealEstateService service;

    private List<Developer> availableDevelopers;

    @Setter
    @Getter
    private RealEstate realEstate;

    @Inject
    public RealEstateEdit(RealEstateService service) {
        this.service = service;
    }

    public Collection<Developer> getAvailableDevelopers() {
        if (availableDevelopers == null) {
            availableDevelopers = service.findAllDevelopers();
        }
        return availableDevelopers;
    }


    @PostConstruct
    public void init() {
        setRealEstate(new RealEstate());
    }

    public String saveRealEstate() {
        service.saveRealEstate(realEstate);
        return "real_estate_list?faces-redirect=true";
    }
}
