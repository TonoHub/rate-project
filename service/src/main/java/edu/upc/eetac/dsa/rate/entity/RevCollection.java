package edu.upc.eetac.dsa.rate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.LoginResource;
import edu.upc.eetac.dsa.rate.RateRootAPIResource;
import edu.upc.eetac.dsa.rate.RevResource;
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

public class RevCollection {

    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Rate Root API"),
            @InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-rev", title = "Current rev"),
            @InjectLink(resource = RevResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-rev", title = "Current rev"),
            @InjectLink(resource = RevResource.class, method = "getRevs", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer rev", bindings = {@Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = RevResource.class, method = "getRevs", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older rev", bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })

    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Rev> rev = new ArrayList<>();

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

    public List<Rev> getRev() {
        return rev;
    }

    public void setRev(List<Rev> rev) {
        this.rev = rev;
    }
}
