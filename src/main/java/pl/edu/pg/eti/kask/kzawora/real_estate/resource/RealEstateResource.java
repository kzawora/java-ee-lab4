package pl.edu.pg.eti.kask.kzawora.real_estate.resource;

import pl.edu.pg.eti.kask.kzawora.housing_community.resource.HousingCommunityResource;
import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.resource.Api;
import pl.edu.pg.eti.kask.kzawora.resource.model.EmbeddedResource;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static pl.edu.pg.eti.kask.kzawora.resource.UriHelper.pagedUri;
import static pl.edu.pg.eti.kask.kzawora.resource.UriHelper.uri;

@Path("realEstates")
public class RealEstateResource {

    private static final int BAD_REQUEST = 400;
    @Context
    private UriInfo info;

    @Inject
    private RealEstateService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")// required for url generation
    public Response getAllRealEstates(@QueryParam("page") @DefaultValue("0") Integer page,
                                      @QueryParam("limit") @DefaultValue("2") Integer limit) {

        if (limit <= 0 || page < 0) {
            return Response.status(BAD_REQUEST).build();
        }

        List<RealEstate> realEstates = service.findAllRealEstates(page * limit, limit);

        realEstates.forEach(realEstate -> realEstate.getLinks().put(
                "self",
                Link.builder().href(uri(info, RealEstateResource.class, "getRealEstate",
                        realEstate.getId()))
                        .method("GET")
                        .build())
        );
        realEstates.forEach(realEstate -> realEstate.getLinks().put(
                "edit",
                Link.builder().href(uri(info, RealEstateResource.class, "getRealEstate",
                        realEstate.getId()))
                        .method("PUT")
                        .build())
        );
        realEstates.forEach(realEstate -> realEstate.getLinks().put(
                "delete",
                Link.builder().href(uri(info, RealEstateResource.class, "getRealEstate",
                        realEstate.getId()))
                        .method("DELETE")
                        .build())
        );

        int size = (int) service.countRealEstates();

        EmbeddedResource.EmbeddedResourceBuilder<List<RealEstate>> builder = EmbeddedResource.<List<RealEstate>>builder()
                .embedded("realEstates", realEstates);

        builder.link(
                "api",
                Link.builder().href(uri(info, Api.class, "getApi")).build());

        builder.link(
                "self",
                Link.builder().href(pagedUri(info, RealEstateResource.class, "getAllRealEstates", page,
                        limit)).build());

        builder.link(
                "first",
                Link.builder().href(pagedUri(info, RealEstateResource.class, "getAllRealEstates", 0,
                        limit)).build());

        int lastPage = size % limit == 0 ? size / limit - 1 : (int) Math.ceil(size / limit);
        builder.link(
                "last",
                Link.builder().href(pagedUri(info, RealEstateResource.class, "getAllRealEstates",
                        lastPage, limit)).build());

        if ((page + 1) * limit < size) {
            builder.link(
                    "next",
                    Link.builder().href(pagedUri(info, RealEstateResource.class, "getAllRealEstates",
                            page + 1, limit)).build());
        }

        if (page > 0) {
            builder.link(
                    "previous",
                    Link.builder().href(pagedUri(info, RealEstateResource.class, "getAllRealEstates",
                            page - 1, limit)).build());
        }

        EmbeddedResource<List<RealEstate>> embedded = builder.build();
        return Response.ok(embedded).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRealEstate(RealEstate realEstate) {
        service.saveRealEstate(realEstate);
        return Response.created(uri(RealEstateResource.class, "getRealEstate", realEstate.getId())).build();
    }

    @GET
    @Path("{realEstateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRealEstate(@PathParam("realEstateId") int realEstateId) {
        RealEstate realEstate = service.findRealEstate(realEstateId);
        if (realEstate != null) {
            realEstate.getLinks().put(
                    "self",
                    Link.builder().href(uri(info, RealEstateResource.class, "getRealEstate",
                            realEstate.getId()))
                            .method("GET")
                            .build());

            realEstate.getLinks().put(
                    "edit",
                    Link.builder().href(uri(info, RealEstateResource.class, "getRealEstate",
                            realEstate.getId()))
                            .method("PUT")
                            .build());

            realEstate.getLinks().put(
                    "delete",
                    Link.builder().href(uri(info, RealEstateResource.class, "getRealEstate",
                            realEstate.getId()))
                            .method("DELETE")
                            .build());

            realEstate.getLinks().put(
                    "developers",
                    Link.builder().href(uri(info, RealEstateResource.class, "getRealEstateDevelopers",
                            realEstate.getId())).build());

            realEstate.getLinks().put(
                    "housingCommunity",
                    Link.builder().href(uri(info, HousingCommunityResource.class, "getHousingCommunity",
                            realEstate.getId())).build());

            realEstate.getLinks().put(
                    "realEstates",
                    Link.builder().href(uri(info, RealEstateResource.class, "getAllRealEstates")).build());
            return Response.ok(realEstate).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("{realEstateId}/developers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRealEstateDevelopers(@PathParam("realEstateId") int realEstateId) {
        RealEstate realEstate = service.findRealEstate(realEstateId);
        if (realEstate != null) {
            EmbeddedResource<List<Developer>> embedded = EmbeddedResource.<List<Developer>>builder()
                    .embedded("developers", realEstate.getDevelopers())
                    .link(
                            "realEstate",
                            Link.builder().href(uri(info, RealEstateResource.class, "getRealEstate",
                                    realEstate.getId())).build())

                    .link(
                            "self",
                            Link.builder().href(uri(info, RealEstateResource.class, "getRealEstateDevelopers",
                                    realEstate.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{realEstateId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRealEstate(@PathParam("realEstateId") int realEstateId, RealEstate realEstate) {
        RealEstate original = service.findRealEstate(realEstateId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != realEstate.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveRealEstate(realEstate);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{realEstateId}")
    public Response deleteRealEstate(@PathParam("realEstateId") int realEstateId) {
        RealEstate original = service.findRealEstate(realEstateId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeRealEstate(original);
            return Response.noContent().build();
        }
    }

}
