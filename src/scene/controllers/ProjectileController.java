package scene.controllers;

import geometry.Vector2D;

import java.util.List;

import scene.actors.Actor;
import scene.actors.ProjectileActor;

public class ProjectileController implements Controller {

  private final ProjectileActor actor;

  public ProjectileController(ProjectileActor actor) {
    this.actor = actor;
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
    actor.tick();
  }

}
