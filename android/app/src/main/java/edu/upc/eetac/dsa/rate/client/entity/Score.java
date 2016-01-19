package edu.upc.eetac.dsa.rate.client.entity;

import java.util.List;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Link;

/**
 * Created by tono on 05/01/2016.
 */
public class Score {

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
