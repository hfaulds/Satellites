package core.net.server;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Controller;
import core.net.msg.ingame.PlayerUpdateMsg;

public class PlayerConnection extends Connection {

  private boolean authenticated = false;
  
  private String username;
  private Actor actor;  
  private Controller controller;

  public void updateActor(PlayerUpdateMsg info) {
    actor.velocity._set(info.velocity);
    actor.spin._set(info.spin);
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(String username) {
    this.authenticated = true;
    this.username = username;
  }

  public void setActor(Actor actor) {
    this.actor = actor;
  }

  public Controller getController() {
    return controller;
  }

  public Actor getActor() {
    return actor;
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }
  
  public String getUsername() {
    return username;
  }
  
}