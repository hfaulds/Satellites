package core.net.msg.pregame;

public class LoginMsg {
  
  public final String username;
  
  public LoginMsg() {
    this.username = null;
  }
  
  public LoginMsg(String username) {
    this.username = username;
  }
  
}
