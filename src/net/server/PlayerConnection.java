package net.server;

import scene.actors.Actor;
import scene.actors.ShipActor;
import scene.controllers.Controller;
import scene.controllers.ServerShipController;
import net.msg.PlayerMsg;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;


public class PlayerConnection extends Connection {

  public final Actor actor;  
  public final Controller controller;  

  public PlayerConnection(Server server) {
    this.actor = new ShipActor(0,0);
    this.controller = new ServerShipController(actor, server);
  }

  public void updateActor(PlayerMsg info) {
    actor.velocity._set(info.velocity);
    actor.spin._set(info.spin);
  }

}