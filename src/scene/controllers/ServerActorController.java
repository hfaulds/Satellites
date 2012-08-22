package scene.controllers;


import java.util.List;

import core.geometry.Vector2D;
import core.net.connections.ServerConnection;

import scene.actors.Actor;

public class ServerActorController implements Controller {

  public final Actor actor;
  private final ServerConnection connection;
  
  public ServerActorController(Actor actor, ServerConnection connection) {
    this.actor = actor;
    this.connection = connection;
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
    connection.sendMsg(actor.getUpdateMsg());
  }

}
