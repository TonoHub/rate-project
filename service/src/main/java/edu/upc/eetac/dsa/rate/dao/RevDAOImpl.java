package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.Rev;
import edu.upc.eetac.dsa.rate.entity.RevCollection;

import java.sql.*;

/**
 * Created by tono on 28/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RevDAOImpl implements RevDAO {

    @Override
    public Rev createRev(String userid, String gameid, String content) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(RevDAOQuery.CREATE_REV);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, gameid);
            stmt.setString(4, content);
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
        return getRevById(id);
    }

    @Override
    public Rev getRevById(String id) throws SQLException {
        Rev rev = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        PreparedStatement stat = null;
        Boolean like;
        int count = 0;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RevDAOQuery.GET_REV_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rev = new Rev();
                rev.setId(rs.getString("id"));
                rev.setUserid(rs.getString("userid"));
                rev.setGameid(rs.getString("gameid"));
                rev.setContent(rs.getString("content"));
                rev.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                rev.setLastModified(rs.getTimestamp("last_modified").getTime());
            }

        } catch (SQLException e) {
            throw e;
        }
        finally {
            if (stmt != null) stmt.close();
        }

        try
        {
            stat = connection.prepareStatement(RevDAOQuery.GET_REV_LIKES_BY_ID);
            stat.setString(1, id);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                like = rs.getBoolean("likes");
                if (like == true)
                count = count + 1;


                else
                count = count - 1;

            }
            rev.setLikes(count);

        }

        catch (SQLException e)
        {

        throw e;
        }

        finally {
            if (stat != null) stat.close();
            if (connection != null) connection.close();
        }





        return rev;
    }

    @Override
    public RevCollection getRev(long timestamp, boolean before) throws SQLException {
        RevCollection stingCollection = new RevCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if(before)
                stmt = connection.prepareStatement(RevDAOQuery.GET_REV);
            else
                stmt = connection.prepareStatement(RevDAOQuery.GET_REV_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Rev sting = new Rev();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setGameid(rs.getString("gameid"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    stingCollection.setNewestTimestamp(sting.getLastModified());
                    first = false;
                }
                stingCollection.setOldestTimestamp(sting.getLastModified());
                stingCollection.getRev().add(sting);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
    }


    @Override
    public RevCollection getRevByGame(String gameid) throws SQLException {

        RevCollection stingCollection = new RevCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RevDAOQuery.GET_REV_BY_GAME);
            stmt.setString(1, new String(gameid));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rev sting = new Rev();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setGameid(rs.getString("gameid"));
                sting.setContent(rs.getString("content"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());

                stingCollection.getRev().add(sting);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
    }


    @Override
    public Rev updateRev(String id, String content) throws SQLException {
        Rev ans = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RevDAOQuery.UPDATE_REV);
            stmt.setString(1, content);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                ans = getRevById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return ans;
    }

    @Override
    public boolean deleteRev(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RevDAOQuery.DELETE_REV);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
