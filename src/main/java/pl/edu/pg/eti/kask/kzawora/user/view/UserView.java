package pl.edu.pg.eti.kask.kzawora.user.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UserView {

    @Setter
    @Getter
    private User user;

}
