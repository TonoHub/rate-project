package edu.upc.eetac.dsa.rate;

import edu.upc.eetac.dsa.rate.entity.RateRootAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by tono on 14/10/2015.
 */
@Path("/")
public class RateRootAPIResource {
    @Context
    private SecurityContext securityContext;

    private String userid;

    @GET
    @Produces(RateMediaType.RATE_ROOT)
    public RateRootAPI getRootAPI() {
        if(securityContext.getUserPrincipal()!=null)
            userid = securityContext.getUserPrincipal().getName();
        RateRootAPI rateRootAPI = new RateRootAPI();

        return rateRootAPI;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
