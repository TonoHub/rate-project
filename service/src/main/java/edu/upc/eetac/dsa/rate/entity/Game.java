package edu.upc.eetac.dsa.rate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.LoginResource;
import edu.upc.eetac.dsa.rate.GameResource;
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

public class Game {

    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Rate Root API"),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-games", title = "Current games"),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-game", title = "Create game", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = GameResource.class, method = "getGame", style = InjectLink.Style.ABSOLUTE, rel = "self game", title = "Game", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
            @InjectLink(resource = GameResource.class, method = "getGames", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer game", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = GameResource.class, method = "getGames", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older game", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")})
    })

    private List<Link> links;
    private String userid;
    private String id;
    private String name;
    private String genre;
    private Integer score;
    private Integer year;
    private long creationTimestamp;
    private long lastModified;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

