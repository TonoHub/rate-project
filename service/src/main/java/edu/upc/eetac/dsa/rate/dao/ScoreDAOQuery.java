package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by tono on 13/12/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public interface ScoreDAOQuery {
    public final static String CREATE_SCORE = "insert into score (userid, gameid, gamescore) values (UNHEX(?), UNHEX(?), ?)";
    public final static String GET_SCORE_BY_ID = "select hex(s.userid) as userid, hex(s.gameid) as gameid, s.gamescore, u.loginid from score s, users u where s.gameid=unhex(?) and u.id=s.userid";
}
