package core.net.msg.pregame;


public class LoginMsg {
  
  public final String username;
  public final String password;
  
  public LoginMsg() {
    this("","");
  }
  
  public LoginMsg(String username, String password) {
    this.username = username;
    this.password = password;
  }
  
}
