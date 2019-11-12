package pl.edu.pg.eti.kask.kzawora.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@Entity
@Table(name = "personalData")
public class PersonalData implements Serializable {
    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters long.")
    @NotBlank(message = "Name must not be blank.")
    private String name;
    @Size(min = 1, max = 50, message = "Surname must be between 1 and 50 characters long.")
    @NotBlank(message = "Surname must not be blank.")
    private String surname;
    @Pattern(regexp = "\\d{11}", message = "PESEL must be 11 digits long.")
    private String pesel;

    public PersonalData(String name, String surname, String pesel) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }

    @OneToOne(mappedBy = "personalData")
    private User user;
}
