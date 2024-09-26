package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//java.util.logging.ConsoleHandler


public class UserDaoJDBCImpl implements UserDao {

    final Logger logger = Logger.getLogger("UserDaoJDBCImpl.class");

    Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "        CREATE TABLE `user` (\n" +
                "  `id` BIGINT(5) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                " `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT(3) NULL,\n" +
                "                PRIMARY KEY (`id`))\n" +
                "        ENGINE = InnoDB\n" +
                "        DEFAULT CHARACTER SET = utf8;";

        int exist = existTableUser();
        if (exist == 0) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
            } catch (SQLException e) {
                logger.log(Level.WARNING,"Error create table" );
                e.printStackTrace();
            }
        }
    }

    public int existTableUser() {
        String sqlTableExist = "SELECT EXISTS (\n" +
                "  SELECT *\n" +
                "  FROM INFORMATION_SCHEMA.TABLES \n" +
                "  WHERE TABLE_SCHEMA = 'kata_schema_users' \n" +
                "  AND TABLE_NAME = 'user' \n" +
                ") AS table_exists;";

        int exist = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlTableExist)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exist = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            logger.log(Level.WARNING,"Error IF EXIST " );
            e.printStackTrace();
        }
        return exist;
    }

    public void dropUsersTable() {

        String sql = "DROP TABLE `user`;";

        int exist = existTableUser();
        if (exist == 1) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into user (name, lastName, age) values (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            logger.log(Level.INFO,"User с именем " + name + " добавлен в базу данных" );
         //   System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Error insert into user " );
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USER WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Error delete user " );
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USER";
        List<User> usersList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                usersList.add(user);
                logger.log(Level.INFO, "All users = "+ user );
               // System.out.println(user);
            }
            return usersList;
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Error getAllUsers " );
            e.printStackTrace();
        }

        return null;
    }

    public void cleanUsersTable() {
        String sql = " TRUNCATE TABLE `user`;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute(sql);
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Error truncate user " );
            e.printStackTrace();
        }
    }
}
