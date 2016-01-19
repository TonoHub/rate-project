package edu.upc.eetac.dsa.rate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by tono on 07/10/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public class AuthToken {

    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Rate Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self login", title = "Login", type= RateMediaType.RATE_AUTH_TOKEN),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-rev", title = "Current rev", type= RateMediaType.RATE_REV_COLLECTION),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-games", title = "Current games", type= RateMediaType.RATE_GAME_COLLECTION),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-game", title = "Create game", type=RateMediaType.RATE_GAME),
            @InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-rev", title = "Create rev", type=RateMediaType.RATE_REV),
            @InjectLink(resource = ScoreResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-score", title = "Create score", type = RateMediaType.RATE_SCORE),
            @InjectLink(resource = LikesResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-likes", title = "Create likes", type = RateMediaType.RATE_LIKES),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", type= RateMediaType.RATE_USER, bindings = @Binding(name = "id", value = "${instance.userid}"))
    })
    private List<Link> links;

    private String userid;
    private String token;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
