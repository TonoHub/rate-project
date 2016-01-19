package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.Likes;

import java.sql.SQLException;

/**
 * Created by tono on 13/12/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public interface LikesDAO {
    public Likes createLikes(String userid, String revid, Boolean likes) throws SQLException;
    public Likes getLikesById(String revid) throws SQLException;
}
