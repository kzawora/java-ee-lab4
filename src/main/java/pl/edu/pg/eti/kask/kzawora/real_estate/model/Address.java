package pl.edu.pg.eti.kask.kzawora.real_estate.model;

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
public class Address implements Serializable {
    @NotBlank(message = "Address must not be blank.")
    @Size(min=2, max=50, message = "Address length should be between 2 and 50 characters.")
    private String address;
    @Pattern(regexp="\\d{2}-\\d{3}", message="Postal code must be in 00-000 format.")
    private String postalCode;
    @NotBlank(message="City must not be blank.")
    @Size(min=2, max=50, message= "City length should be between 2 and 50 characters.")
    private String city;
}
