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
  public void tick(long dt, List<Actor> actors) {
    Vector2D force = new Vector2D();
    
    for(Actor other : actors) {
      if(other != actor) {
        force._add(actor.gravForceFrom(other));
      }
    }

    actor.applyForce(force);
    actor.tick(dt);
  }

}
