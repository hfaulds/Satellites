package core.db;

import org.hibernate.Session;

public class UserModel {

  public static User findByUsername(String username, Session session) {
    session.beginTransaction();
    User user = (User)session.get(User.class, username);
    session.getTransaction().commit();
    session.close();
    return user;
  }
}
