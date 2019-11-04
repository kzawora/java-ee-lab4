package pl.edu.pg.eti.kask.kzawora.user.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;
import pl.edu.pg.eti.kask.kzawora.user.UserService;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserEdit implements Serializable {

    private UserService service;

    private List<Developer> availableDevelopers;

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
        service.saveUser(user);
        return "real_estate_list?faces-redirect=true";
    }
}
