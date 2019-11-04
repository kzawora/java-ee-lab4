package pl.edu.pg.eti.kask.kzawora.housing_community.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.housing_community.HousingCommunityService;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class HousingCommunityView {

    private List<RealEstate> managedRealEstates;

    private HousingCommunityService service;

    @Inject
    public HousingCommunityView(HousingCommunityService service) {
        this.service = service;
    }

    public List<RealEstate> getManagedRealEstates() {
        List<RealEstate> realEstatesList = new ArrayList<>();
        for (RealEstate realEstate : service.getRealEstateService().findAllRealEstates()) {
            try {
                if (realEstate.getHousingCommunity().equals(housingCommunity)) {
                    realEstatesList.add(realEstate);
                }
            } catch (NullPointerException e) {
            }
        }
        managedRealEstates = realEstatesList;
        return managedRealEstates;
    }

    @Setter
    @Getter
    private HousingCommunity housingCommunity;

}
