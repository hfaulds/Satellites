package core.net;

import scene.actors.Actor;
import scene.actors.ShipActor;
import scene.controllers.Controller;
import scene.controllers.ServerShipController;

import com.esotericsoftware.kryonet.Connection;

import core.net.connections.ServerConnection;
import core.net.msg.PlayerUpdateMsg;

public class Player extends Connection {

  public final Actor actor;  
  public final Controller controller;  

  public Player(ServerConnection connection) {
    this.actor = new ShipActor(0,0);
    this.controller = new ServerShipController(actor, connection);
  }

  public void updateActor(PlayerUpdateMsg info) {
    actor.velocity._set(info.velocity);
    actor.spin._set(info.spin);
  }

}