package scene.controllers;

import geometry.Vector2D;

import java.util.List;

import scene.actors.Actor;

import com.esotericsoftware.kryonet.Server;




public class ServerSatelliteController implements Controller {

  public final Actor actor;
  private final Server server;
  
  public ServerSatelliteController(Actor actor, Server server) {
    this.actor = actor;
    this.server = server;
  }

  @Override
  public void tick(List<Actor> actors) {
    if(actor.id > 0) {
      Vector2D force = new Vector2D();
      
      for(Actor other : actors) {
        if(other != actor) {
          if(!actor.collides(other)) {
            force._add(actor.gravForceFrom(other));
          } else {
            actor.velocity._mult(0.9);
          }
        }
      }
  
      actor.applyForce(force);
      actor.tick();
      server.sendToAllUDP(actor.getInfo());
    }
  }

}
