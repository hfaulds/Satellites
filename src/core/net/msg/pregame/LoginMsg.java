package core.net.msg.pregame;

import core.db.User;

public class LoginMsg {
  
  public final String username;
  
  public LoginMsg() {
    this.username = null;
  }
  
  public LoginMsg(User user) {
    this.username = user.getUsername();
  }
  
}
