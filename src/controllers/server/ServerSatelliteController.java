package controllers.server;

import java.util.List;

import controllers.Controller;

import math.Vector2D;

import actors.Actor;

public class ServerSatelliteController extends Controller {

  public ServerSatelliteController(Actor actor) {
    super(actor);
  }

  @Override
  public void tick(List<Actor> actors) {
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
  }

}
