package core.db.models;

import org.hibernate.Session;

import core.db.entities.UserEntity;

public class UserModel {

  public static UserEntity findByUsername(String username, Session session) {
    session.beginTransaction();
    UserEntity user = (UserEntity)session.get(UserEntity.class, username);
    session.getTransaction().commit();
    return user;
  }
}
