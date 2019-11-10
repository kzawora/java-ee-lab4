package pl.edu.pg.eti.kask.kzawora.real_estate.view.converter;

import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = RealEstate.class, managed = true)
@Dependent
public class RealEstateConverter implements Converter<RealEstate> {

    private RealEstateService service;

    @Inject
    public RealEstateConverter(RealEstateService service) {
        this.service = service;
    }

    @Override
    public RealEstate getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return new RealEstate();
        }
        final int id = Integer.parseInt(value);
        return id == 0 ? new RealEstate() : service.findRealEstate(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RealEstate value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
