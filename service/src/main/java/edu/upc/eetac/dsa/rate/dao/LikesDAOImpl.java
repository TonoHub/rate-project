package edu.upc.eetac.dsa.rate.dao;

import edu.upc.eetac.dsa.rate.entity.Likes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tono on 13/12/2015.
 */
public class LikesDAOImpl implements LikesDAO {

    @Override
    public Likes createLikes(String userid, String revid, Boolean likes) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(LikesDAOQuery.CREATE_LIKES);
            stmt.setString(1, userid);
            stmt.setString(2, revid);
            stmt.setBoolean(3, likes);
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
        return getLikesById(revid);
    }

    @Override
    public Likes getLikesById(String revid) throws SQLException {
        Likes likes = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(LikesDAOQuery.GET_LIKES_BY_ID);
            stmt.setString(1, revid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                likes = new Likes();
                likes.setUserid(rs.getString("userid"));
                likes.setRevid(rs.getString("reviewid"));
                likes.setLikes(rs.getBoolean("likes"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return likes;
    }

}

