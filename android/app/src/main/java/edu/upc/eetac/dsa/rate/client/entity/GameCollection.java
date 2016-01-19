package edu.upc.eetac.dsa.rate.client.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tono on 19/12/2015.
 */
public class GameCollection {
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

    public List<Game> getGames() {
        return game;
    }

    public void setGames(List<Game> games) {this.game = games;}
}
