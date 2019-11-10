package pl.edu.pg.eti.kask.kzawora.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalData implements Serializable {
    @Size(min=1, max=50, message = "Name must be between 1 and 50 characters long.")
    @NotBlank(message="Name must not be blank.")
    private String name;
    @Size(min=1, max=50, message = "Surname must be between 1 and 50 characters long.")
    @NotBlank(message="Surname must not be blank.")
    private String surname;
    @Pattern(regexp="\\d{11}", message="PESEL must be 11 digits long.")
    private String pesel;
}
