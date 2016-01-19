package edu.upc.eetac.dsa.rate;

import edu.upc.eetac.dsa.rate.dao.RevDAO;
import edu.upc.eetac.dsa.rate.dao.RevDAOImpl;
import edu.upc.eetac.dsa.rate.entity.AuthToken;
import edu.upc.eetac.dsa.rate.entity.Rev;
import edu.upc.eetac.dsa.rate.entity.RevCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by tono on 28/10/2015.
 */
@Path("rev")
public class RevResource {
    @Context
    private SecurityContext securityContext;



    @POST
    public Response createRev(@FormParam("gameid") String gameid, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if (content == null)
            throw new BadRequestException("all parameters are mandatory");
        RevDAO stingDAO = new RevDAOImpl();
        Rev sting = null;
        AuthToken authenticationToken = null;
        try {
            sting = stingDAO.createRev(securityContext.getUserPrincipal().getName(),gameid,content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getId());
        return Response.created(uri).type(RateMediaType.RATE_REV).entity(sting).build();
    }

    @GET
    @Produces(RateMediaType.RATE_REV_COLLECTION)
    public RevCollection getRevs(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        RevCollection stingCollection = null;
        RevDAO stingDAO = new RevDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            stingCollection = stingDAO.getRev(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }


    @Path("/gameid/{gameid}")
    @GET
    @Produces(RateMediaType.RATE_REV_COLLECTION)
    public RevCollection getRevsByGame(@PathParam("gameid") String gameid) {
        RevCollection stingCollection = null;
        RevDAO stingDAO = new RevDAOImpl();
        try {
            stingCollection = stingDAO.getRevByGame(gameid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(RateMediaType.RATE_REV)
    public Response getRev(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Rev sting = null;
        RevDAO stingDAO = new RevDAOImpl();
        try {
            sting = stingDAO.getRevById(id);
            if (sting == null)
                throw new NotFoundException("Rev with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(sting.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(sting).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


    @Path("/{id}")
    @PUT
    @Consumes(RateMediaType.RATE_REV)
    @Produces(RateMediaType.RATE_REV)
    public Rev updateURev(@PathParam("id") String id, Rev sting) {
        if(sting == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(sting.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");
        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(sting.getUserid()))
            throw new ForbiddenException("operation not allowed");

        RevDAO stingDAO = new RevDAOImpl();
        try {
            sting = stingDAO.updateRev(id, sting.getContent());
            if(sting == null)
                throw new NotFoundException("Rev with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return sting;
    }

    @Path("/{id}")
    @DELETE
    public void deleteRev(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        RevDAO stingDAO = new RevDAOImpl();
        try {
            String ownerid = stingDAO.getRevById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!stingDAO.deleteRev(id))
                throw new NotFoundException("Rev with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}

