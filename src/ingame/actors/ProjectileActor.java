package ingame.actors;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ProjectileActor extends Actor {
  
  public static final double  SPEED   = 0.001;
  public static final int     DAMAGE  = 100;
  
  private static final double MASS    = 0.002;
  private static Mesh         MESH    = MeshLoader.loadMesh("Projectile-Mk2.obj");

  public ProjectileActor(Vector2D position, Rotation rotation, double mass, boolean visible, int id) {
    super(position, rotation, mass, MESH, visible, id);
  }
  
  public ProjectileActor(Vector2D position, Vector2D direction, Vector2D shipVelocity, Actor owner) {
    super(position, Rotation.XRotFromVector(direction), MASS, MESH);
    this.velocity._set(shipVelocity.add(direction._mult(SPEED)));
  }
}