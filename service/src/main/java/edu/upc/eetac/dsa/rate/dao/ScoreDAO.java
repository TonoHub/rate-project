package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.Score;

import java.sql.SQLException;

@JsonInclude(JsonInclude.Include.NON_NULL)

/**
 * Created by tono on 13/12/2015.
 */
public interface ScoreDAO {
    public Score createScore(String userid, String gameid, Integer score) throws SQLException;
    public Score getScoreById(String gameid) throws SQLException;
}
