package pl.edu.pg.eti.kask.kzawora.real_estate.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "realEstate")
@ToString(exclude = "realEstate")
@Entity
@Table(name = "addresses")
public class Address implements Serializable {
    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @NotBlank(message = "Address must not be blank.")
    @Size(min=2, max=50, message = "Address length should be between 2 and 50 characters.")
    private String address;
    @Pattern(regexp="\\d{2}-\\d{3}", message="Postal code must be in 00-000 format.")
    private String postalCode;
    @NotBlank(message="City must not be blank.")
    @Size(min=2, max=50, message= "City length should be between 2 and 50 characters.")
    private String city;

    public Address(String address, String postalCode, String city) {
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }

    @OneToOne(mappedBy = "address")
    private RealEstate realEstate;
}
