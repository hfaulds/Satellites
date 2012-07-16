package scene.actors;

import scene.graphics.ProjectileGraphic;
import geometry.Vector2D;

public class ProjectileActor extends Actor {

  private static final double MASS = 0.002;

  public ProjectileActor(Vector2D position) {
    super(position.x, position.y, MASS, new ProjectileGraphic());
  }

  @Override
  public boolean collides(Actor a) {
    return false;
  }

}
