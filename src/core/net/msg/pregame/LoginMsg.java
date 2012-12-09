package core.net.msg.pregame;

import core.db.entities.UserEntity;

public class LoginMsg {
  
  public final String username;
  
  public LoginMsg() {
    this.username = null;
  }
  
  public LoginMsg(UserEntity user) {
    this.username = user.getUsername();
  }
  
}
