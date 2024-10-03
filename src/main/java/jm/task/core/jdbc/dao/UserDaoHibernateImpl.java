package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.query.NativeQuery;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final String  CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `user` ( `id` BIGINT(5) NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NOT NULL,`lastName` VARCHAR(45) NOT NULL, `age` TINYINT(3) NULL,  PRIMARY KEY (`id`))  ENGINE = InnoDB  DEFAULT CHARACTER SET = utf8;";
    private final String  DROP_TABLE = "DROP TABLE IF EXISTS `user`;";
    private static final Logger logger = Logger.getLogger("UserDaoHibernateImpl.class");
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(CREATE_TABLE);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error create table");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(DROP_TABLE);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try ( Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.WARNING, "Error save user");
//            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try ( Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user =  session.get(User.class, id);
            if(user !=null){
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.WARNING, "Error remove user");
            //e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
             transaction = session.beginTransaction();
            Query query = session.createQuery("from User ");
            List<User> userList = query.getResultList();
            transaction.commit();
            return userList;
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.WARNING, "Error get all users");
           // e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from User");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.WARNING, "Error clean table");
           // e.printStackTrace();
        }
    }
}
