package edu.upc.eetac.dsa.rate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.LoginResource;
import edu.upc.eetac.dsa.rate.RevResource;
import edu.upc.eetac.dsa.rate.RateRootAPIResource;
import edu.upc.eetac.dsa.rate.UserResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by tono on 27/10/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Rev {
    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Rate Root API"),
            @InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-rev", title = "Current rev"),
            @InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-rev", title = "Create rev", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = RevResource.class, method = "getRev", style = InjectLink.Style.ABSOLUTE, rel = "self rev", title = "rev", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = RevResource.class, method = "getRevsByGame", style = InjectLink.Style.ABSOLUTE, rel = "rev-game", title = "rev-game", bindings = @Binding(name = "gameid", value = "${instance.gameid}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
            @InjectLink(resource = RevResource.class, method = "getRevs", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer rev", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = RevResource.class, method = "getRevs", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older rev", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")})
    })
    private List<Link> links;
    private String id;
    private String userid;
    private String gameid;
    private Integer likes;
    private String content;
    private long creationTimestamp;
    private long lastModified;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}

