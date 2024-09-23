package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "        CREATE TABLE `kata_schema_users`.`user` (\n" +
                "  `id` BIGINT(5) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                " `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT(3) NULL,\n" +
                "                PRIMARY KEY (`id`))\n" +
                "        ENGINE = InnoDB\n" +
                "        DEFAULT CHARACTER SET = utf8;";

        String sqlTableExist = "SELECT EXISTS (\n" +
                "  SELECT *\n" +
                "  FROM INFORMATION_SCHEMA.TABLES \n" +
                "  WHERE TABLE_SCHEMA = 'kata_schema_users' \n" +
                "  AND TABLE_NAME = 'user' \n" +
                ") AS table_exists;";

          int exist = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlTableExist);
          while (resultSet.next()){
              exist = resultSet.getInt(1);
              System.out.println(exist);
          }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (exist == 0) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void dropUsersTable() {

        String sql = "DROP TABLE `kata_schema_users`.`user`;";

        String sqlTableExist = "SELECT EXISTS (\n" +
                "  SELECT *\n" +
                "  FROM INFORMATION_SCHEMA.TABLES \n" +
                "  WHERE TABLE_SCHEMA = 'kata_schema_users' \n" +
                "  AND TABLE_NAME = 'user' \n" +
                ") AS table_exists;";

        int exist = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlTableExist);
            while (resultSet.next()){
                exist = resultSet.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (exist == 1) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into user (name, lastName, age) values (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name +" добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USER WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USER";
        List<User> usersList= new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                usersList.add(user);
                System.out.println(user);
            }
            return usersList;
        }
        catch ( SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void cleanUsersTable() {
        String sql = " TRUNCATE TABLE `kata_schema_users`.`user`;";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
