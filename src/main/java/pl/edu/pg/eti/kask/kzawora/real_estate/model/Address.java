package pl.edu.pg.eti.kask.kzawora.real_estate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address implements Serializable {
    private String address;
    private String postalCode;
    private String city;
}
