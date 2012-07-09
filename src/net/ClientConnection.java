package net;

import actors.Actor;

import com.esotericsoftware.kryonet.Connection;

public class ClientConnection extends Connection {

  public final Actor actor;  
  
  public ClientConnection(Actor actor) {
    this.actor = actor;
  }

  public void updateActor(PlayerInfo info) {
    actor.velocity._set(info.velocity);
    actor.spin._set(info.spin);
  }

}