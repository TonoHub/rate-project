package edu.upc.eetac.dsa.rate.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.rate.entity.User;

import java.sql.SQLException;

/**
 * Created by tono on 07/10/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public interface UserDAO {
    public User createUser(String loginid, String password, String email) throws SQLException, UserAlreadyExistsException;

    public User updateProfile(String id, String email) throws SQLException;

    public User getUserById(String id) throws SQLException;

    public User getUserByLoginid(String loginid) throws SQLException;

    public boolean deleteUser(String id) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;
}
