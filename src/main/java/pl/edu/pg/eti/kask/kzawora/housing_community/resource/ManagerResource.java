package pl.edu.pg.eti.kask.kzawora.housing_community.resource;

import pl.edu.pg.eti.kask.kzawora.housing_community.HousingCommunityService;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("managers")
public class ManagerResource {

    @Inject
    private HousingCommunityService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Manager> getAllManagers() {
        return service.findAllManagers();
    }

}

