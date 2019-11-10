package pl.edu.pg.eti.kask.kzawora.user.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.user.UserService;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static pl.edu.pg.eti.kask.kzawora.user.HashUtils.sha256;

@Named
@ViewScoped
public class UserEdit implements Serializable {

    private UserService service;

    @Setter
    @Getter
    private String unhashedPassword = null;

    @Getter
    @Setter
    private List<RealEstate> selectedRealEstates;

    @Getter
    private List<String> availableRoles;

    @Setter
    @Getter
    private User user;

    private List<String> getAllRoles() {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(User.Roles.class.getFields()));
        List<String> res = new ArrayList<>();
        for (Field f : fields) {
            if (f.getType() == String.class) {
                try {
                    res.add((String) f.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    @Inject
    public UserEdit(UserService service) {
        this.availableRoles = getAllRoles();
        this.service = service;
    }


    @PostConstruct
    public void init() {
        setUser(new User());
    }

    public String saveUser() {
        if (unhashedPassword != null && !unhashedPassword.isEmpty()) {
            user.setPassword(sha256(unhashedPassword));
        }
        user.setRealEstates(new HashSet<>(selectedRealEstates));
        service.saveUser(user);
        return "user_list?faces-redirect=true";
    }
}
