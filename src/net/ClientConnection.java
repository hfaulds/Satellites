package net;

import net.msg.PlayerInfo;
import actors.Actor;
import actors.ShipActor;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import controllers.Controller;
import controllers.ServerShipController;

public class ClientConnection extends Connection {

  public final Actor actor;  
  public final Controller controller;  

  public ClientConnection(Server server) {
    this.actor = new ShipActor(0,0);
    this.controller = new ServerShipController(actor, server);
  }

  public void updateActor(PlayerInfo info) {
    actor.velocity._set(info.velocity);
    actor.spin._set(info.spin);
  }

}