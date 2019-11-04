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
    @NotBlank
    @Size(min=2, max=100)
    private String address;
    @Pattern(regexp="\\d{2}-\\d{3}")
    private String postalCode;
    @NotBlank
    @Size(min=2, max=100)
    private String city;
}
