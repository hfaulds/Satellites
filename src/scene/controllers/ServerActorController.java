package scene.controllers;

import geometry.Vector2D;

import java.util.List;

import scene.actors.Actor;

import com.esotericsoftware.kryonet.Server;

public class ServerActorController implements Controller {

  public final Actor actor;
  private final Server server;
  
  public ServerActorController(Actor actor, Server server) {
    this.actor = actor;
    this.server = server;
  }

  @Override
  public void tick(long dt, List<Actor> actors) {
    Vector2D force = new Vector2D();
    
    for(Actor other : actors) {
      if(other != actor) {
        force._add(actor.gravForceFrom(other));
      }
    }

    actor.applyForce(force);
    actor.tick(dt);
    server.sendToAllUDP(actor.getUpdateMsg());
  }

}
