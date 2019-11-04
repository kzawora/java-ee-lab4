package pl.edu.pg.eti.kask.kzawora.housing_community.view;

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
public class HousingCommunityList {

    private HousingCommunityService service;

    private List<HousingCommunity> housingCommunities;

    @Inject
    public HousingCommunityList(HousingCommunityService service) {
        this.service = service;
    }

    public List<HousingCommunity> getHousingCommunities() {
        if (housingCommunities == null) {
            housingCommunities = service.findAllHousingCommunities();
        }
        return housingCommunities;
    }

    public String removeHousingCommunity(HousingCommunity housingCommunity) {
        for (RealEstate realEstate : service.getRealEstateService().findAllRealEstates()) {
            try {
                if (realEstate.getHousingCommunity().equals(housingCommunity)) {
                    realEstate.setHousingCommunity(null);
                    service.getRealEstateService().saveRealEstate(realEstate);
                }
            } catch (NullPointerException e) {
            }
        }
        service.removeHousingCommunity(housingCommunity);
        return "housing_community_list?faces-redirect=true";
    }

    public String init() {
        service.init();
        return "housing_community_list?faces-redirect=true";
    }
}
