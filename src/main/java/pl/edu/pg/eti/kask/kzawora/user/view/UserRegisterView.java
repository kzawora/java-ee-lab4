package pl.edu.pg.eti.kask.kzawora.user.view;

import lombok.Getter;
import pl.edu.pg.eti.kask.kzawora.user.view.model.RegistrationForm;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * In future this will be used to register new users. Now its only for validation.
 *
 * @author psysiu
 */
@ViewScoped
@Named
public class UserRegisterView implements Serializable {

    /**
     * Registration data.
     */
    @Getter
    private RegistrationForm form = new RegistrationForm();

    public String register() {
        //cal some business logic
        return "user_registered?faces-redirect=true";
    }
}
