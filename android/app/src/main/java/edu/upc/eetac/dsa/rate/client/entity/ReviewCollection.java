package edu.upc.eetac.dsa.rate.client.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tono on 05/01/2016.
 */
public class ReviewCollection {
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Review> rev = new ArrayList<>();

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

    public List<Review> getReviews() {
        return rev;
    }

    public void setReviews(List<Review> reviews) {
        this.rev = reviews;
    }
}
