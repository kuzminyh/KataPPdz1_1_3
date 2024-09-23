package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/kata_schema_users";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

public static Connection getConnection(){
    try {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      //  Statement statement = connection.createStatement()){
    //      statement.execute("INSERT INTO user( name, lastName, age) VALUES ('serg', 'sergio', 15 )");
//        if (!connection.isClosed()) {
//            System.out.println("We are connected!");
//        }
        return connection;
    } catch (SQLException e) {
        System.out.println("there is no connection... Exception!");
    };
return null;
}


}
