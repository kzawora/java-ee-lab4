package pl.edu.pg.eti.kask.kzawora.housing_community.resource;


import pl.edu.pg.eti.kask.kzawora.housing_community.HousingCommunityService;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;
import pl.edu.pg.eti.kask.kzawora.resource.Api;
import pl.edu.pg.eti.kask.kzawora.resource.model.EmbeddedResource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static pl.edu.pg.eti.kask.kzawora.resource.UriHelper.pagedUri;
import static pl.edu.pg.eti.kask.kzawora.resource.UriHelper.uri;

@Path("housingCommunities")
public class HousingCommunityResource {

    private static final int BAD_REQUEST = 400;
    @Context
    private UriInfo info;

    @Inject
    private HousingCommunityService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")// required for url generation
    public Response getAllHousingCommunities(@QueryParam("page") @DefaultValue("0") Integer page,
                                             @QueryParam("limit") @DefaultValue("2") Integer limit) {

        if (limit <= 0 || page < 0) {
            return Response.status(BAD_REQUEST).build();
        }

        List<HousingCommunity> housingCommunities = service.findAllHousingCommunities(page * limit, limit);

        housingCommunities.forEach(housingCommunity -> housingCommunity.getLinks().put(
                "self",
                pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                        .href(uri(info, HousingCommunityResource.class,
                                "getHousingCommunity", housingCommunity.getId()))
                        .method("GET")
                        .build())
        );
        housingCommunities.forEach(housingCommunity -> housingCommunity.getLinks().put(
                "edit",
                pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                        .href(uri(info, HousingCommunityResource.class,
                                "getHousingCommunity", housingCommunity.getId()))
                        .method("PUT")
                        .build())
        );
        housingCommunities.forEach(housingCommunity -> housingCommunity.getLinks().put(
                "delete",
                pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                        .href(uri(info, HousingCommunityResource.class,
                                "getHousingCommunity", housingCommunity.getId()))
                        .method("DELETE")
                        .build())
        );

        int size = (int) service.countHousingCommunities();

        EmbeddedResource.EmbeddedResourceBuilder<List<HousingCommunity>> builder = EmbeddedResource.<List<HousingCommunity>>builder()
                .embedded("housingCommunities", housingCommunities);

        builder.link(
                "api",
                pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                        .href(uri(info, Api.class, "getApi"))
                        .build());

        builder.link(
                "self",
                pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                        .href(pagedUri(info, HousingCommunityResource.class, "getAllHousingCommunities",
                                page, limit))
                        .build());

        builder.link(
                "first",
                pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                        .href(pagedUri(info, HousingCommunityResource.class, "getAllHousingCommunities",
                                0, limit))
                        .build());

        int lastPage = size % limit == 0 ? size / limit - 1 : (int) Math.ceil(size / limit);
        builder.link(
                "last",
                pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                        .href(pagedUri(info, HousingCommunityResource.class, "getAllHousingCommunities",
                                lastPage, limit))
                        .build());

        if ((page + 1) * limit < size) {
            builder.link(
                    "next",
                    pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                            .href(pagedUri(info, HousingCommunityResource.class, "getAllHousingCommunities",
                                    page + 1, limit))
                            .build());
        }

        if (page > 0) {
            builder.link(
                    "previous",
                    pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                            .href(pagedUri(info, HousingCommunityResource.class, "getAllHousingCommunities",
                                    page - 1, limit))
                            .build());
        }

        EmbeddedResource<List<HousingCommunity>> embedded = builder.build();
        return Response.ok(embedded).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveHousingCommunity(HousingCommunity housingCommunity) {
        service.saveHousingCommunity(housingCommunity);
        return Response.created(uri(HousingCommunityResource.class,
                "getHousingCommunity", housingCommunity.getId()))
                .build();
    }

    @GET
    @Path("{housingCommunityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHousingCommunity(@PathParam("housingCommunityId") int k) {
        HousingCommunity housingCommunity = service.findHousingCommunity(k);
        if (housingCommunity != null) {
            housingCommunity.getLinks().put(
                    "self",
                    pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                            .href(uri(info, HousingCommunityResource.class,
                                    "getHousingCommunity", housingCommunity.getId()))
                            .method("GET")
                            .build());

            housingCommunity.getLinks().put(
                    "edit",
                    pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                            .href(uri(info, HousingCommunityResource.class,
                                    "getHousingCommunity", housingCommunity.getId()))
                            .method("PUT")
                            .build());

            housingCommunity.getLinks().put(
                    "delete",
                    pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                            .href(uri(info, HousingCommunityResource.class,
                                    "getHousingCommunity", housingCommunity.getId()))
                            .method("DELETE")
                            .build());

            housingCommunity.getLinks().put(
                    "manager",
                    pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                            .href(uri(info, HousingCommunityResource.class,
                                    "getHousingCommunityManager", housingCommunity.getId()))
                            .build());

            housingCommunity.getLinks().put(
                    "housingCommunities",
                    pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                            .href(uri(info, HousingCommunityResource.class,
                                    "getAllHousingCommunities"))
                            .build());
            return Response.ok(housingCommunity).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("{housingCommunityId}/manager")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHousingCommunityManager(@PathParam("housingCommunityId") int housingCommunityId) {
        HousingCommunity housingCommunity = service.findHousingCommunity(housingCommunityId);
        if (housingCommunity != null) {
            EmbeddedResource<Manager> embedded = EmbeddedResource.<Manager>builder()
                    .embedded("manager", housingCommunity.getManager())
                    .link(
                            "housingCommunity",
                            pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                                    .href(uri(info, HousingCommunityResource.class,
                                            "getHousingCommunity", housingCommunity.getId()))
                                    .build())

                    .link(
                            "self",
                            pl.edu.pg.eti.kask.kzawora.resource.model.Link.builder()
                                    .href(uri(info, HousingCommunityResource.class,
                                            "getHousingCommunityManager", housingCommunity.getId()))
                                    .build())
                    .build();
            return Response.ok(embedded).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{housingCommunityId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHousingCommunity(@PathParam("housingCommunityId") int housingCommunityId,
                                           HousingCommunity housingCommunity) {
        HousingCommunity original = service.findHousingCommunity(housingCommunityId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != housingCommunity.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveHousingCommunity(housingCommunity);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{housingCommunityId}")
    public Response deleteHousingCommunity(@PathParam("housingCommunityId") int housingCommunityId) {
        HousingCommunity original = service.findHousingCommunity(housingCommunityId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeHousingCommunity(original);
            return Response.noContent().build();
        }
    }
}
