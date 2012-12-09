package core.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import core.Actor;

@Entity
@Table(name = "USERS")
public class UserEntity {
  
  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @OneToOne()
  private ActorEntity actor;
  
  public UserEntity() {
    this("","", null);
  }
  
  public UserEntity(String username, String password, ActorEntity actor) {
     this.username = username;
     this.password = password;
     this.actor = actor;
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
  
  public Actor getActor() {
    return actor.toActor();
  }
  
}
