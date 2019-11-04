package pl.edu.pg.eti.kask.kzawora.real_estate.resource;


import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("developers")
public class DeveloperResource {

    @Inject
    private RealEstateService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Developer> getAllDevelopers() {
        return service.findAllDevelopers();
    }

}

