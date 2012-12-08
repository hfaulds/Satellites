package core.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class UserModel {

  public static User findByUsername(String username) {
    Configuration configuration = new Configuration().configure();
    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    User user = (User)session.get(User.class, username);
    session.getTransaction().commit();
    session.close();
    return user;
  }
}
