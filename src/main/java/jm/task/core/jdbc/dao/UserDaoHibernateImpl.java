package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger log = Logger.getLogger(UserDaoHibernateImpl.class.getName());


    public UserDaoHibernateImpl() {
        // TODO document why this constructor is empty
    }


    @Override
    public void createUsersTable() {
        String sql = "        CREATE TABLE `user` (\n" +
                "  `id` BIGINT(5) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                " `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT(3) NULL,\n" +
                "                PRIMARY KEY (`id`))\n" +
                "        ENGINE = InnoDB\n" +
                "        DEFAULT CHARACTER SET = utf8;";

    try (Session session = Util.getSessionFactory().openSession()){
        session.beginTransaction();
        String query =  "create table if not exists user (id BIGINT auto_increment primary key, name varchar(80) " +
                "not null, lastName varchar(80) not null, age tinyint not null);";
        session.createNativeQuery(query).executeUpdate();
        session.getTransaction().commit();

    }
    catch (Exception e) {
        log.log(Level.SEVERE, "Error creating table", e);
    }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String query = "drop table if exists user";
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error dropping table", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error saving user", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DELETE FROM User WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error removing user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try  (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
            log.log(Level.INFO, "All users = "+ users );
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Error creating table", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error cleaning table", e);
        }
    }
}
