package pl.edu.pg.eti.kask.kzawora.real_estate.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RealEstateView {

    @Setter
    @Getter
    private RealEstate realEstate;

}
