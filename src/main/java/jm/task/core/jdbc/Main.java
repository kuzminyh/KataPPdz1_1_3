package jm.task.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private final static String URL = "jdbc:mysql://localhost:3306/kata_schema_users";

    private final static String USERNAME = "root";

    private final static String PASSWORD = "root";


    public static void main(String[] args) {

        Connection connection;
        try {

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (!connection.isClosed()) {

                System.out.println("We are connected!");

            }

        } catch (SQLException e) {

            System.out.println("there is no connection... Exception!");

        }

    }
}
