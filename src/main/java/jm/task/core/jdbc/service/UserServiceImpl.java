package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.*;

import java.util.List;

public class UserServiceImpl implements UserService {
    //UserDaoJDBCImpl userDaoJDBCImpl = new UserDaoJDBCImpl();
    UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();

    public void createUsersTable() throws SQLException {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
//        String sql = "DROP TABLE `kata_schema_users`.`user`;";
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);

    }

    public List<User> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        return allUsers;
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
