package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.Game;
import edu.upc.eetac.dsa.rate.entity.GameCollection;

import java.sql.*;

/**
 * Created by tono on 28/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GameDAOImpl implements GameDAO {

    @Override
    public Game createGame(String name, String genre, Integer year) throws SQLException {
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

            stmt = connection.prepareStatement(GameDAOQuery.CREATE_GAME);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, genre);
            stmt.setInt(4, year);
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
        return getGameById(id);
    }

    @Override
    public Game getGameById(String id) throws SQLException {
        Game game = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        PreparedStatement stat = null;
        int scores = 0;
        int games = 0;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GameDAOQuery.GET_GAME_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                game = new Game();
                game.setId(rs.getString("id"));
                game.setName(rs.getString("name"));
                game.setGenre(rs.getString("genre"));
                game.setYear(rs.getInt("year"));
                game.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                game.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
        }

        try {
            stat = connection.prepareStatement(GameDAOQuery.GET_GAME_SCORE_BY_ID);
            stat.setString(1, id);

            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                scores = scores + rs.getInt("gamescore");
                games = games + 1;
            }
            if (games != 0)
            game.setScore(Math.round(scores/games));
        }
        catch (SQLException e) {
            throw e;
        } finally {
            if (stat != null) stat.close();
            if (connection != null) connection.close();
        }
        return game;
    }

    @Override
    public Game getGameByNam(String name) throws SQLException {
        Game game = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        PreparedStatement stat = null;
        int scores = 0;
        int games = 0;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GameDAOQuery.GET_GAME_BY_NAME);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                game = new Game();
                game.setId(rs.getString("id"));
                game.setName(rs.getString("name"));
                game.setGenre(rs.getString("genre"));
                game.setYear(rs.getInt("year"));
                game.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                game.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
        }

        try {
            String id = game.getId();
            stat = connection.prepareStatement(GameDAOQuery.GET_GAME_SCORE_BY_ID);
            stat.setString(1, id);

            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                scores = scores + rs.getInt("gamescore");
                games = games + 1;
            }
            if (games != 0)
                game.setScore(Math.round(scores/games));
        }
        catch (SQLException e) {
            throw e;
        } finally {
            if (stat != null) stat.close();
            if (connection != null) connection.close();
        }
        return game;
    }

    @Override
    public GameCollection getGame(long timestamp, boolean before) throws SQLException {
        GameCollection stingCollection = new GameCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if(before)
                stmt = connection.prepareStatement(GameDAOQuery.GET_GAME);
            else
                stmt = connection.prepareStatement(GameDAOQuery.GET_GAME_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Game sting = new Game();
                sting.setId(rs.getString("id"));
                sting.setName(rs.getString("name"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    stingCollection.setNewestTimestamp(sting.getLastModified());
                    first = false;
                }
                stingCollection.setOldestTimestamp(sting.getLastModified());
                stingCollection.getGame().add(sting);
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
    public GameCollection getGamesByGen(String genre) throws SQLException {
        GameCollection stingCollection = new GameCollection();

        Connection connection = null;
        PreparedStatement stmt = null;

            try {
                connection = Database.getConnection();
                stmt = connection.prepareStatement(GameDAOQuery.GET_GAMES_BY_GENRE);
                stmt.setString(1, new String(genre));

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Game sting = new Game();
                    sting.setId(rs.getString("id"));
                    sting.setName(rs.getString("name"));
                    sting.setGenre(rs.getString("genre"));
                    sting.setYear(rs.getInt("year"));
                    sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                    sting.setLastModified(rs.getTimestamp("last_modified").getTime());

                    stingCollection.getGame().add(sting);
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
    public Game updateGame(String id, String name, String genre, Integer score, Integer year) throws SQLException {
        Game topic = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GameDAOQuery.UPDATE_GAME);
            stmt.setString(1, name);
            stmt.setString(2, genre);
            stmt.setInt(3, year);
            stmt.setString(4, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                topic = getGameById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return topic;
    }

    @Override
    public boolean deleteGame(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GameDAOQuery.DELETE_GAME);
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

