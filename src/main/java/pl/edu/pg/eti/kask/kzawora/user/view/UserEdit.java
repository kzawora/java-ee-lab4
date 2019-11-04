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
import java.util.HashSet;
import java.util.List;

@Named
@ViewScoped
public class UserEdit implements Serializable {

    private UserService service;

    @Getter
    @Setter
    private List<RealEstate> selectedRealEstates;

    @Setter
    @Getter
    private User user;

    @Inject
    public UserEdit(UserService service) {
        this.service = service;
    }


    @PostConstruct
    public void init() {
        setUser(new User());
    }

    public String saveUser() {
        user.setRealEstates(new HashSet<>(selectedRealEstates));
        service.saveUser(user);
        return "user_list?faces-redirect=true";
    }
}
