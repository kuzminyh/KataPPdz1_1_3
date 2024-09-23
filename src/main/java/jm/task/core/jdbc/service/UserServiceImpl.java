package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.*;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDaoJDBCImpl userDaoJDBCImpl = new UserDaoJDBCImpl();

    public void createUsersTable() throws SQLException {
        userDaoJDBCImpl.createUsersTable();
    }

    public void dropUsersTable() {
//        String sql = "DROP TABLE `kata_schema_users`.`user`;";
       userDaoJDBCImpl.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoJDBCImpl.saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        userDaoJDBCImpl.removeUserById(id);

    }

    public List<User> getAllUsers() {
        List<User> allUsers = userDaoJDBCImpl.getAllUsers();
        return allUsers;
    }

    public void cleanUsersTable() {
       userDaoJDBCImpl.cleanUsersTable();
    }
}
