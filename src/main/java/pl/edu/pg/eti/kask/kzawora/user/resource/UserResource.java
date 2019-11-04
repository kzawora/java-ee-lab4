package pl.edu.pg.eti.kask.kzawora.user.resource;

import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.real_estate.resource.RealEstateResource;
import pl.edu.pg.eti.kask.kzawora.resource.Api;
import pl.edu.pg.eti.kask.kzawora.resource.model.EmbeddedResource;
import pl.edu.pg.eti.kask.kzawora.resource.model.Link;
import pl.edu.pg.eti.kask.kzawora.user.UserService;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

import static pl.edu.pg.eti.kask.kzawora.resource.UriHelper.pagedUri;
import static pl.edu.pg.eti.kask.kzawora.resource.UriHelper.uri;

@Path("users")
public class UserResource {

    private static final int BAD_REQUEST = 400;
    @Context
    private UriInfo info;

    @Inject
    private UserService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")// required for url generation
    public Response getAllUsers(@QueryParam("page") @DefaultValue("0") Integer page,
                                @QueryParam("limit") @DefaultValue("2") Integer limit) {

        if (limit <= 0 || page < 0) {
            return Response.status(BAD_REQUEST).build();
        }

        List<User> users = service.findAllUsers(page * limit, limit);

        users.forEach(user -> user.getLinks().put(
                "self",
                Link.builder().href(uri(info, UserResource.class, "getUser", user.getId()))
                        .method("GET")
                        .build())
        );
        users.forEach(user -> user.getLinks().put(
                "edit",
                Link.builder().href(uri(info, UserResource.class, "getUser", user.getId()))
                        .method("PUT")
                        .build())
        );
        users.forEach(user -> user.getLinks().put(
                "delete",
                Link.builder().href(uri(info, UserResource.class, "getUser", user.getId()))
                        .method("DELETE")
                        .build())
        );

        int size = (int) service.countUsers();

        EmbeddedResource.EmbeddedResourceBuilder<List<User>> builder = EmbeddedResource.<List<User>>builder()
                .embedded("users", users);

        builder.link(
                "api",
                Link.builder().href(uri(info, Api.class, "getApi")).build());

        builder.link(
                "self",
                Link.builder().href(pagedUri(info, UserResource.class, "getAllUsers", page,
                        limit)).build());

        builder.link(
                "first",
                Link.builder().href(pagedUri(info, UserResource.class, "getAllUsers", 0,
                        limit)).build());

        int lastPage = size % limit == 0 ? size / limit - 1 : (int) Math.ceil(size / limit);
        builder.link(
                "last",
                Link.builder().href(pagedUri(info, UserResource.class, "getAllUsers",
                        lastPage, limit)).build());

        if ((page + 1) * limit < size) {
            builder.link(
                    "next",
                    Link.builder().href(pagedUri(info, UserResource.class, "getAllUsers",
                            page + 1, limit)).build());
        }

        if (page > 0) {
            builder.link(
                    "previous",
                    Link.builder().href(pagedUri(info, UserResource.class, "getAllUsers",
                            page - 1, limit)).build());
        }

        EmbeddedResource<List<User>> embedded = builder.build();
        return Response.ok(embedded).build();
    }


    @GET
    @Path("{userId}/realEstates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRealEstates(@PathParam("userId") int userId) {
        User user = service.findUser(userId);
        if (user != null) {
            List<RealEstate> realEstates = new ArrayList<RealEstate>();
            realEstates.addAll(user.getRealEstates());

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

            EmbeddedResource<List<RealEstate>> embedded = EmbeddedResource.<List<RealEstate>>builder()
                    .embedded("realEstates", realEstates)
                    .link(
                            "user",
                            Link.builder().href(uri(info, UserResource.class, "getUser",
                                    user.getId())).build())

                    .link(
                            "self",
                            Link.builder().href(uri(info, UserResource.class, "getRealEstates",
                                    user.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) {
        service.saveUser(user);
        return Response.created(uri(UserResource.class, "getUser", user.getId())).build();
    }

    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") int userId) {
        User user = service.findUser(userId);
        if (user != null) {
            user.getLinks().put(
                    "self",
                    Link.builder().href(uri(info, UserResource.class, "getUser", user.getId()))
                            .method("GET")
                            .build());

            user.getLinks().put(
                    "delete",
                    Link.builder().href(uri(info, UserResource.class, "getUser", user.getId()))
                            .method("DELETE")
                            .build());

            user.getLinks().put(
                    "edit",
                    Link.builder().href(uri(info, UserResource.class, "getUser", user.getId()))
                            .method("PUT")
                            .build());

            user.getLinks().put(
                    "realEstates",
                    Link.builder().href(uri(info, UserResource.class, "getRealEstates",
                            user.getId())).build());

            user.getLinks().put(
                    "users",
                    Link.builder().href(uri(info, UserResource.class, "getAllUsers")).build());
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") int userId, User user) {
        User original = service.findUser(userId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != user.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveUser(user);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") int userId) {
        User original = service.findUser(userId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeUser(original);
            return Response.noContent().build();
        }
    }

}
