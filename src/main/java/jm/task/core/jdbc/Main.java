package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

//Сначала надо создать схему kata_schema_users в MySql
public class Main {
    public static void main(String[] args) {
 //       Util.getConnection();
//        UserDao userDao = new UserDaoJDBCImpl();
        UserDao userDao = new UserDaoHibernateImpl();
       userDao.createUsersTable();

        userDao.saveUser("Name5", "LastName1", (byte) 20);
        userDao.saveUser("Name6", "LastName2", (byte) 25);
        userDao.saveUser("Name7", "LastName3", (byte) 31);
        userDao.saveUser("Name8", "LastName4", (byte) 38);

        userDao.removeUserById(1);
        userDao.getAllUsers();
      userDao.cleanUsersTable();
      userDao.dropUsersTable();
    }
}
