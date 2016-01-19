package edu.upc.eetac.dsa.rate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.LoginResource;
import edu.upc.eetac.dsa.rate.RateRootAPIResource;
import edu.upc.eetac.dsa.rate.ScoreResource;
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

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Score {
    @InjectLinks({
            @InjectLink(resource = RateRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Rate Root API"),
            @InjectLink(resource = ScoreResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-score", title = "Current score"),
            @InjectLink(resource = ScoreResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-score", title = "Create score", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = ScoreResource.class, method = "getScore", style = InjectLink.Style.ABSOLUTE, rel = "self score", title = "score", bindings = @Binding(name = "id", value = "${instance.gameid}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
    })
    private List<Link> links;
    private String userid;
    private String gameid;
    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
