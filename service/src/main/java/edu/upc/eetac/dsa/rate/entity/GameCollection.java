package edu.upc.eetac.dsa.rate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.GameResource;
import edu.upc.eetac.dsa.rate.LoginResource;
import edu.upc.eetac.dsa.rate.RateRootAPIResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tono on 28/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)



public class GameCollection {

    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-games", title = "Current games"),
            @InjectLink(resource = GameResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-games", title = "Current games"),
            @InjectLink(resource = GameResource.class, method = "getGames", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer games", bindings = {@Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = GameResource.class, method = "getGames", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older games", bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })

    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Game> game = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Game> getGame() {
        return game;
    }

    public void setGame(List<Game> game) {
        this.game = game;
    }
}
