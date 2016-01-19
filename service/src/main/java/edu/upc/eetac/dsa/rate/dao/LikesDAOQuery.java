package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by tono on 13/12/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public interface LikesDAOQuery {
    public final static String CREATE_LIKES = "insert into likes (userid, reviewid, likes) values (UNHEX(?), UNHEX(?), ?)";
    public final static String GET_LIKES_BY_ID = "select hex(s.userid) as userid, hex(s.reviewid) as reviewid, s.likes, u.loginid from likes s, users u where s.reviewid=unhex(?) and u.id=s.userid";
}
