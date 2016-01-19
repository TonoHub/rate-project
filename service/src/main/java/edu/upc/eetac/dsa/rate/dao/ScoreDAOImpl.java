package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tono on 13/12/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ScoreDAOImpl implements ScoreDAO {

    @Override
    public Score createScore(String userid, String gameid, Integer score) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(ScoreDAOQuery.CREATE_SCORE);
            stmt.setString(1, userid);
            stmt.setString(2, gameid);
            stmt.setInt(3, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getScoreById(gameid);
    }

    @Override
    public Score getScoreById(String gameid) throws SQLException {
        Score score = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ScoreDAOQuery.GET_SCORE_BY_ID);
            stmt.setString(1, gameid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                score = new Score();
                score.setUserid(rs.getString("userid"));
                score.setGameid(rs.getString("gameid"));
                score.setScore(rs.getInt("gamescore"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return score;
    }

}
