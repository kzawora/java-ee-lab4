package pl.edu.pg.eti.kask.kzawora.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalData implements Serializable {
    private String name;
    private String surname;
    private String pesel;
}
