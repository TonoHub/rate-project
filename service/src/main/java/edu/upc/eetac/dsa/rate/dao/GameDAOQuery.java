package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by tono on 27/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public interface GameDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_GAME = "insert into game (id, name, genre, year) values (UNHEX(?), ?, ?, ?)";
    public final static String GET_GAME_BY_ID = "select hex(s.id) as id, s.name, s.genre, s.year, s.creation_timestamp, s.last_modified from game s where s.id=unhex(?)";
    public final static String GET_GAME_SCORE_BY_ID = "select s.gamescore from score s where s.gameid=unhex(?)";
    public final static String GET_GAME_BY_NAME = "select hex(s.id) as id, s.name, s.genre, s.year, s.creation_timestamp, s.last_modified from game s where s.name=?";
    public final static String GET_GAMES_BY_GENRE = "select hex(id) as id, name, genre, year, creation_timestamp, last_modified from game where genre = ?";
    public final static String GET_GAME = "select hex(id) as id, name, creation_timestamp, last_modified from game where creation_timestamp < ? order by creation_timestamp desc limit 5";
    public final static String GET_GAME_AFTER = "select hex(id) as id, name, creation_timestamp, last_modified from game  where creation_timestamp > ? order by creation_timestamp desc limit 5";
    public final static String UPDATE_GAME = "update game set name=?, genre=?, year=? where id=unhex(?) ";
    public final static String DELETE_GAME = "delete from game where id=unhex(?)";
}
