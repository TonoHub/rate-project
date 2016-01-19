package edu.upc.eetac.dsa.rate;

import edu.upc.eetac.dsa.rate.dao.GameDAO;
import edu.upc.eetac.dsa.rate.dao.GameDAOImpl;
import edu.upc.eetac.dsa.rate.entity.AuthToken;
import edu.upc.eetac.dsa.rate.entity.Game;
import edu.upc.eetac.dsa.rate.entity.GameCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by tono on 28/10/2015.
 */
@Path("game")
public class GameResource {
    @Context
    private SecurityContext securityContext;


    @POST
    public Response createGame(@FormParam("name") String name, @FormParam("genre") String genre, @FormParam("year") Integer year, @Context UriInfo uriInfo) throws URISyntaxException {
        if (name == null || genre == null || year == null)
            throw new BadRequestException("all parameters are mandatory");
        GameDAO stingDAO = new GameDAOImpl();
        Game sting = null;
        AuthToken authenticationToken = null;
        try {
            sting = stingDAO.createGame(name, genre, year);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getId());
        return Response.created(uri).type(RateMediaType.RATE_GAME).entity(sting).build();
    }

    @GET
    @Produces(RateMediaType.RATE_GAME_COLLECTION)
    public GameCollection getGames(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        GameCollection stingCollection = null;
        GameDAO stingDAO = new GameDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            stingCollection = stingDAO.getGame(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }


    @Path("/genre/{genre}")
    @GET
    @Produces(RateMediaType.RATE_GAME_COLLECTION)
    public GameCollection getGamesByGenre(@PathParam("genre") String genre) {
        GameCollection stingCollection = null;
        GameDAO stingDAO = new GameDAOImpl();
        try {
            stingCollection = stingDAO.getGamesByGen(genre);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }


    @Path("/name/{name}")
    @GET
    @Produces(RateMediaType.RATE_GAME)
    public Response getGameByName(@PathParam("name") String name, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Game sting = null;
        GameDAO stingDAO = new GameDAOImpl();
        try {
            sting = stingDAO.getGameByNam(name);
            if (sting == null)
                throw new NotFoundException("Game with name = " + name + " doesn't exist");

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
    @GET
    @Produces(RateMediaType.RATE_GAME)
    public Response getGame(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Game sting = null;
        GameDAO stingDAO = new GameDAOImpl();
        try {
            sting = stingDAO.getGameById(id);
            if (sting == null)
                throw new NotFoundException("Game with id = " + id + " doesn't exist");

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
    @Consumes(RateMediaType.RATE_GAME)
    @Produces(RateMediaType.RATE_GAME)
    public Game updateUGame(@PathParam("id") String id, Game sting) {
        if(sting == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(sting.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        GameDAO stingDAO = new GameDAOImpl();
        try {
            sting = stingDAO.updateGame(id, sting.getName(), sting.getGenre(), sting.getScore(), sting.getYear());
            if(sting == null)
                throw new NotFoundException("Game with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return sting;
    }

    @Path("/{id}")
    @DELETE
    public void deleteGame(@PathParam("id") String id) {
        GameDAO stingDAO = new GameDAOImpl();
        try {
            if(!stingDAO.deleteGame(id))
                throw new NotFoundException("Game with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}

