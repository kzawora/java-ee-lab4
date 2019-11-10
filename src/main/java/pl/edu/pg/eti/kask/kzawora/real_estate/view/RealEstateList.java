package pl.edu.pg.eti.kask.kzawora.real_estate.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class RealEstateList {

    private RealEstateService service;

    private List<RealEstate> realEstates;

    @Getter
    @Setter
    private Integer selectedDeveloper;

    @Inject
    public RealEstateList(RealEstateService service) {
        this.service = service;
    }

    public List<RealEstate> getRealEstates() {
        if (selectedDeveloper != null) {
            return service.findByDeveloper(service.findDeveloper(selectedDeveloper));
        }
        if (realEstates == null) {
            realEstates = service.findAllRealEstates();
        }
        return realEstates;
    }

    public String removeRealEstate(RealEstate realEstate) {
        service.removeRealEstate(realEstate);
        return "real_estate_list?faces-redirect=true";
    }

    public List<RealEstate> findByDeveloper(Developer d) {
        return service.findByDeveloper(d);
    }

    public String init() {
        service.init();
        return "real_estate_list?faces-redirect=true";
    }
}
