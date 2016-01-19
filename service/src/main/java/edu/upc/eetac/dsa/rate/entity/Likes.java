package edu.upc.eetac.dsa.rate.entity;

import edu.upc.eetac.dsa.rate.LikesResource;
import edu.upc.eetac.dsa.rate.LoginResource;
import edu.upc.eetac.dsa.rate.RateRootAPIResource;
import edu.upc.eetac.dsa.rate.UserResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by tono on 13/12/2015.
 */
public class Likes {
    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Rate Root API"),
            @InjectLink(resource = LikesResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-likes", title = "Current likes"),
            @InjectLink(resource = LikesResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-likes", title = "Create likes", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = LikesResource.class, method = "getLikes", style = InjectLink.Style.ABSOLUTE, rel = "self likes", title = "likes", bindings = @Binding(name = "id", value = "${instance.revid}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getLikes", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
    })
    private List<Link> links;
    private String userid;
    private String revid;
    private Boolean likes;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRevid() {
        return revid;
    }

    public void setRevid(String revid) {
        this.revid = revid;
    }

    public Boolean getLikes() {
        return likes;
    }

    public void setLikes(Boolean likes) {
        this.likes = likes;
    }

}
