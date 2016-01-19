package edu.upc.eetac.dsa.rate;

import edu.upc.eetac.dsa.rate.dao.ScoreDAO;
import edu.upc.eetac.dsa.rate.dao.ScoreDAOImpl;
import edu.upc.eetac.dsa.rate.entity.AuthToken;
import edu.upc.eetac.dsa.rate.entity.Score;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by tono on 13/12/2015.
 */

@Path("score")
public class ScoreResource {
    @Context
    private SecurityContext securityContext;


    @POST
    public Response createScore(@FormParam("gameid") String gameid, @FormParam("score") Integer score, @Context UriInfo uriInfo) throws URISyntaxException {
        if (gameid == null || score == null)
            throw new BadRequestException("all parameters are mandatory");
        ScoreDAO scoreDAO = new ScoreDAOImpl();
        Score sting = null;
        AuthToken authenticationToken = null;
        try {
            sting = scoreDAO.createScore(securityContext.getUserPrincipal().getName(),gameid,score);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getGameid());
        return Response.created(uri).type(RateMediaType.RATE_SCORE).entity(sting).build();
    }


    @Path("/{gameid}")
    @GET
    @Produces(RateMediaType.RATE_SCORE)
    public Score getScore(@PathParam("gameid") String gameid, @Context Request request) {
        // Create cache-control
        Score score = null;
        try {
            score = (new ScoreDAOImpl()).getScoreById(gameid);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if (score == null)
            throw new NotFoundException("Game with id = " + gameid + " doesn't exist");
        return score;
    }

}
