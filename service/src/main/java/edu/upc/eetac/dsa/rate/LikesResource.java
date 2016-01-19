package edu.upc.eetac.dsa.rate;

import edu.upc.eetac.dsa.rate.dao.LikesDAO;
import edu.upc.eetac.dsa.rate.dao.LikesDAOImpl;
import edu.upc.eetac.dsa.rate.entity.AuthToken;
import edu.upc.eetac.dsa.rate.entity.Likes;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by tono on 13/12/2015.
 */

@Path("likes")

public class LikesResource {
    @Context
    private SecurityContext securityContext;


    @POST
    public Response createLikes(@FormParam("revid") String revid, @FormParam("likes") Boolean likes, @Context UriInfo uriInfo) throws URISyntaxException {
        if (revid == null || likes == null)
            throw new BadRequestException("all parameters are mandatory");
        LikesDAO likesDAO = new LikesDAOImpl();
        Likes sting = null;
        AuthToken authenticationToken = null;
        try {
            sting = likesDAO.createLikes(securityContext.getUserPrincipal().getName(),revid,likes);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getRevid());
        return Response.created(uri).type(RateMediaType.RATE_LIKES).entity(sting).build();
    }


    @Path("/{revid}")
    @GET
    @Produces(RateMediaType.RATE_LIKES)
    public Likes getLikes(@PathParam("revid") String revid, @Context Request request) {
        // Create cache-control
        Likes likes = null;
        try {
            likes = (new LikesDAOImpl()).getLikesById(revid);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if (likes == null)
            throw new NotFoundException("Review with id = " + revid + " doesn't exist");
        return likes;
    }

}


