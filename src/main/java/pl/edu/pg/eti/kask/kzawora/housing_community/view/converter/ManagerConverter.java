package pl.edu.pg.eti.kask.kzawora.housing_community.view.converter;

import pl.edu.pg.eti.kask.kzawora.housing_community.HousingCommunityService;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Manager.class, managed = true)
@Dependent
public class ManagerConverter implements Converter<Manager> {

    private HousingCommunityService service;

    @Inject
    public ManagerConverter(HousingCommunityService service) {
        this.service = service;
    }

    @Override
    public Manager getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return service.findManager(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Manager value) {
        if (value == null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
