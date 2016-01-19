package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.Game;
import edu.upc.eetac.dsa.rate.entity.GameCollection;

import java.sql.SQLException;

/**
 * Created by tono on 27/10/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public interface GameDAO {
    public Game createGame(String name, String genre, Integer year) throws SQLException;
    public Game getGameById(String id) throws SQLException;
    public Game getGameByNam(String name) throws SQLException;
    public GameCollection getGamesByGen(String genre) throws SQLException;
    public GameCollection getGame(long timestamp, boolean before) throws SQLException;
    public Game updateGame(String id, String name, String genre, Integer score, Integer year) throws SQLException;
    public boolean deleteGame(String id) throws SQLException;
}
