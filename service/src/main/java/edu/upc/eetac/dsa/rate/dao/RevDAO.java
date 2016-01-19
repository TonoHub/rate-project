package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.Rev;
import edu.upc.eetac.dsa.rate.entity.RevCollection;

import java.sql.SQLException;

/**
 * Created by tono on 27/10/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public interface RevDAO {
    public Rev createRev(String userid, String gameid, String content) throws SQLException;
    public Rev getRevById(String id) throws SQLException;
    public RevCollection getRevByGame(String gameid) throws SQLException;
    public RevCollection getRev (long timestamp, boolean before) throws SQLException;
    public Rev updateRev(String id, String content) throws SQLException;
    public boolean deleteRev(String id) throws SQLException;
}
