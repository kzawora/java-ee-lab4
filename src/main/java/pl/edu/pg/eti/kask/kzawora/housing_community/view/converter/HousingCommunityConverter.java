package pl.edu.pg.eti.kask.kzawora.housing_community.view.converter;

import pl.edu.pg.eti.kask.kzawora.housing_community.HousingCommunityService;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = HousingCommunity.class, managed = true)
@Dependent
public class HousingCommunityConverter implements Converter<HousingCommunity> {

    private HousingCommunityService service;

    @Inject
    public HousingCommunityConverter(HousingCommunityService service) {
        this.service = service;
    }

    @Override
    public HousingCommunity getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            HousingCommunity hc = new HousingCommunity();
            hc.setRealEstates(null);
            return hc;
            
        }
        final int id = Integer.parseInt(value);
        return id == 0 ? new HousingCommunity() : service.findHousingCommunity(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, HousingCommunity value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
