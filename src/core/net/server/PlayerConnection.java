package core.net.server;

import ingame.actors.ShipActor;
import ingame.controllers.ServerShipController;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Controller;
import core.net.msg.ingame.PlayerUpdateMsg;

public class PlayerConnection extends Connection {

  private boolean authenticated = false;
  
  public final Actor actor;  
  public final Controller controller;  

  public PlayerConnection(ServerConnection connection) {
    this.actor = new ShipActor(0,0);
    this.controller = new ServerShipController(actor, connection);
  }

  public void updateActor(PlayerUpdateMsg info) {
    actor.velocity._set(info.velocity);
    actor.spin._set(info.spin);
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

}