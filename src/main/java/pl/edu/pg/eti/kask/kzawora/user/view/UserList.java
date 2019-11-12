package pl.edu.pg.eti.kask.kzawora.user.view;

import pl.edu.pg.eti.kask.kzawora.user.UserService;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserList {

    private UserService service;

    private List<User> users;

    @Inject
    public UserList(UserService service) {
        this.service = service;
    }

    public List<User> getUsers() {
        if (users == null) {
            users = service.findAllUsers();
        }
        return users;
    }

    public String removeUser(User user) {
        service.removeUser(user);
        return "/users/user_list?faces-redirect=true";
    }

}
