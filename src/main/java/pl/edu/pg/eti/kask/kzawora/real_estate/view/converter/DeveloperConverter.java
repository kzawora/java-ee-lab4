package pl.edu.pg.eti.kask.kzawora.real_estate.view.converter;

import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Developer.class, managed = true)
@Dependent
public class DeveloperConverter implements Converter<Developer> {

    private RealEstateService service;

    @Inject
    public DeveloperConverter(RealEstateService service) {
        this.service = service;
    }

    @Override
    public Developer getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return new Developer();
        }
        return service.findDeveloper(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Developer value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
