package pl.edu.pg.eti.kask.kzawora.resource;


import pl.edu.pg.eti.kask.kzawora.housing_community.resource.HousingCommunityResource;
import pl.edu.pg.eti.kask.kzawora.real_estate.resource.RealEstateResource;
import pl.edu.pg.eti.kask.kzawora.resource.model.EmbeddedResource;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;
import pl.edu.pg.eti.kask.kzawora.user.resource.UserResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class Api {

    @Context
    private UriInfo info;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApi() {
        EmbeddedResource<Void> embedded = EmbeddedResource.<Void>builder()
                .link("realEstate", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(RealEstateResource.class)
                                .path(RealEstateResource.class, "getAllRealEstates")
                                .build())
                        .build())
                .link("housingCommunity", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(HousingCommunityResource.class)
                                .path(HousingCommunityResource.class, "getAllHousingCommunities")
                                .build())
                        .build())
                .link("user", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(UserResource.class)
                                .path(UserResource.class, "getAllUsers")
                                .build())
                        .build())
                .link("self", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(Api.class)
                                .path(Api.class, "getApi")
                                .build())
                        .build())
                .build();
        return Response.ok(embedded).build();
    }

}
