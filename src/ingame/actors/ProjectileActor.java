package ingame.actors;

import core.Actor;
import core.ActorInfo;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ProjectileActor extends Actor {
  
  public static final double  SPEED   = 0.01;
  public static final int     DAMAGE  = 100;
  
  private static final double MASS    = 0.002;
  
  private static String       MESH    = "Projectile-Mk2.obj";

  public ProjectileActor(Vector2D position, Rotation rotation, double mass, int id, String mesh) {
    super(new ActorInfo(position, rotation, mass, mesh, id));
  }
  
  public ProjectileActor(Vector2D position, Vector2D direction, Vector2D shipVelocity) {
    super(new ActorInfo(position, Rotation.XRotFromVector(direction), MASS, MESH));
    this.velocity._set(shipVelocity.add(direction._multiply(SPEED)));
  }
}