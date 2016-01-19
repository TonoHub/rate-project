package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by tono on 27/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public interface RevDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_REV = "insert into review (id, userid, gameid, content) values (UNHEX(?), UNHEX(?), UNHEX(?), ?)";
    public final static String GET_REV_BY_ID = "select hex(s.id) as id, hex(s.userid) as userid, hex(s.gameid) as gameid, s.content, s.creation_timestamp, s.last_modified, u.loginid from review s, users u where s.id=unhex(?) and u.id=s.userid";
    public final static String GET_REV_LIKES_BY_ID = "select s.likes from likes s where s.reviewid=unhex(?)";
    public final static String GET_REV_BY_GAME = "select hex(id) as id, hex(userid) as userid, hex(gameid) as gameid, content, creation_timestamp, last_modified from review where gameid=unhex(?)";
    public final static String GET_REV = "select hex(id) as id, hex(userid) as userid, hex(gameid) as gameid, creation_timestamp, last_modified from review where creation_timestamp < ? order by creation_timestamp desc limit 5";
    public final static String GET_REV_AFTER = "select hex(id) as id, hex(userid) as userid, hex(gameid) as gameid, creation_timestamp, last_modified from review where creation_timestamp > ? order by creation_timestamp desc limit 5";
    public final static String UPDATE_REV = "update review set content=? where id=unhex(?) ";
    public final static String DELETE_REV = "delete from review where id=unhex(?)";
}
