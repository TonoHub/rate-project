package edu.upc.eetac.dsa.rate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by tono on 07/10/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public class User {
    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Rate Root API"),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-games", title = "Current games", type= RateMediaType.RATE_GAME_COLLECTION),
            @InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-rev", title = "Current rev", type= RateMediaType.RATE_REV_COLLECTION),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-game", title = "Create game", type=RateMediaType.RATE_GAME),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "self user-profile", title = "User profile", type= RateMediaType.RATE_USER, bindings = @Binding(name = "id", value = "${instance.id}"))
    })
    private List<Link> links;
    private String id;
    private String loginid;
    private String email;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


