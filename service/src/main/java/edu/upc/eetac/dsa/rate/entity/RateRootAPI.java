package edu.upc.eetac.dsa.rate.entity;

import edu.upc.eetac.dsa.rate.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by tono on 14/10/2015.
 */



public class RateRootAPI {
    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "Rate Root API")
            ,@InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "login", title = "Login",  type= RateMediaType.RATE_AUTH_TOKEN)
            ,@InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-user", title = "Register", type= RateMediaType.RATE_AUTH_TOKEN)
            ,@InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-rev", title = "Current rev", type= RateMediaType.RATE_REV_COLLECTION)
            ,@InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-games", title = "Current games", type= RateMediaType.RATE_GAME_COLLECTION)
            ,@InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout", condition="${!empty resource.userid}")
            ,@InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-game", title = "Create game", condition="${!empty resource.userid}", type=RateMediaType.RATE_GAME)
            ,@InjectLink(resource = UserResource.class, method="getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", condition="${!empty resource.userid}", type= RateMediaType.RATE_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))
    })
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
