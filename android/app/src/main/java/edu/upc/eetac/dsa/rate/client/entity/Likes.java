package edu.upc.eetac.dsa.rate.client.entity;

import java.util.List;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Link;

/**
 * Created by tono on 05/01/2016.
 */
public class Likes {
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
