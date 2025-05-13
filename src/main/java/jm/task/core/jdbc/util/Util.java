package jm.task.core.jdbc.util;

//import java.lang.module.Configuration;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/kata_schema_users";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    private static Connection connection;
    private static SessionFactory sessionFactory;

    private static final Logger log = Logger.getLogger(Util.class.getName());

    static {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.username", USERNAME);
        configuration.setProperty("hibernate.connection.password", PASSWORD);
        configuration.setProperty("hibernate.connection.url", URL);
        configuration.setProperty("current_session_context_class", "thread");
        configuration.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

private Util() {}

public static Connection getConnection(){
    try {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        return connection;
    } catch (SQLException e) {
        System.out.println("there is no connection... Exception!");
    }

return null;
}

public static SessionFactory getSessionFactory(){
        return sessionFactory;
}

}
