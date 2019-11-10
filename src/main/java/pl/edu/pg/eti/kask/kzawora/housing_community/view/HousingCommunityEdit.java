package pl.edu.pg.eti.kask.kzawora.housing_community.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.housing_community.HousingCommunityService;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;
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
public class HousingCommunityEdit implements Serializable {

    private HousingCommunityService service;

    private List<Manager> availableManagers;

    @Getter
    @Setter
    private List<RealEstate> selectedRealEstates;

    @Setter
    @Getter
    private HousingCommunity housingCommunity;

    @Inject
    public HousingCommunityEdit(HousingCommunityService service) {
        this.service = service;
    }

    public Collection<Manager> getAvailableManagers() {
        if (availableManagers == null) {
            availableManagers = service.findAllManagers();
        }
        return availableManagers;
    }

    @PostConstruct
    public void init() {
        setHousingCommunity(new HousingCommunity());
    }

    public String saveHousingCommunity() {
        service.saveHousingCommunity(housingCommunity);
        try {
            for (RealEstate realEstate : selectedRealEstates) {
                realEstate.setHousingCommunity(housingCommunity);
                service.getRealEstateService().saveRealEstate(realEstate);
            }
        } catch (NullPointerException ex) {
        }
        return "housing_community_list?faces-redirect=true";
    }
}
