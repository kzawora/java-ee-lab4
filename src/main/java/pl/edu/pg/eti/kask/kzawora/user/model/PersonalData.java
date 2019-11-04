package pl.edu.pg.eti.kask.kzawora.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalData implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Pattern(regexp="\\d{11}")
    private String pesel;
}
