package scene.actors;

import scene.graphics.ProjectileGraphic;
import geometry.Vector2D;

public class ProjectileActor extends Actor {

  private static final double MASS = 0.002;
  private static final double SPEED = 2;

  public ProjectileActor(Vector2D position, Vector2D direction) {
    super(position.x, position.y, MASS, new ProjectileGraphic());
    this.velocity._set(direction._mult(SPEED));
  }

  @Override
  public boolean collides(Actor a) {
    return false;
  }

}
