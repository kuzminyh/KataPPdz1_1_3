package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
//        CREATE TABLE `kata_schema_users`.`user` (
//  `id` INT NOT NULL AUTO_INCREMENT,
//  `name` VARCHAR(45) NOT NULL,
//  `lastName` VARCHAR(45) NOT NULL,
//  `age` INT(3) NULL,
//                PRIMARY KEY (`id`))
//        ENGINE = InnoDB
//        DEFAULT CHARACTER SET = utf8;
    }

    public void dropUsersTable() {

    }

    public void saveUser(String name, String lastName, byte age) {

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
