package pl.edu.pg.eti.kask.kzawora.user.validation;


import pl.edu.pg.eti.kask.kzawora.user.view.model.RegistrationForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Validates if provided passwords are the same.
 *
 * @author psysiu
 */
public class RepeatedPasswordsValidator implements ConstraintValidator<RepeatedPasswords, RegistrationForm> {

    @Override
    public boolean isValid(RegistrationForm value, ConstraintValidatorContext context) {
        return Objects.equals(value.getPassword(), value.getRepeatPassword());
    }
}
