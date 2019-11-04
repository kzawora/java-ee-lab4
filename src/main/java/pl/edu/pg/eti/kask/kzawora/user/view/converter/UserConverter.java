package pl.edu.pg.eti.kask.kzawora.user.view.converter;

import pl.edu.pg.eti.kask.kzawora.user.UserService;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = User.class, managed = true)
@Dependent
public class UserConverter implements Converter<User> {

    private UserService service;

    @Inject
    public UserConverter(UserService service) {
        this.service = service;
    }

    @Override
    public User getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        final int id = Integer.parseInt(value);
        return id == 0 ? new User() : service.findUser(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, User value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
