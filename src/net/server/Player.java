package net.server;

import scene.actors.Actor;
import scene.actors.ShipActor;
import scene.controllers.Controller;
import scene.controllers.ServerShipController;
import net.msg.PlayerUpdateMsg;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;


public class Player extends Connection {

  public final Actor actor;  
  public final Controller controller;  

  public Player(Server server) {
    this.actor = new ShipActor(0,0);
    this.controller = new ServerShipController(actor, server);
  }

  public void updateActor(PlayerUpdateMsg info) {
    actor.velocity._set(info.velocity);
    actor.spin._set(info.spin);
  }

}