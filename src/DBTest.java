import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import core.db.User;

public class DBTest {
  public static void main(String... args) {
    Configuration configuration = new Configuration().configure();   
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    System.out.println(((User)session.get(User.class, "test2")).getPassword());
    session.getTransaction().commit();
    session.close();
  }
}
