package core.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Entity
@Table(name = "USERS")
public class User {
  
  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  public User() {
    
  }
  
  public User(String username, String password) {
     this.username = username;
     this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public static User findByUsername(String username) {
    Configuration configuration = new Configuration().configure();   
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    User user = (User)session.get(User.class, username);
    session.getTransaction().commit();
    session.close();
    return user;
  }
}
