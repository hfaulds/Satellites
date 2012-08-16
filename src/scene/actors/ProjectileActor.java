package scene.actors;

import geometry.Mesh;
import geometry.MeshLoader;
import geometry.Rotation;
import geometry.Vector2D;

public class ProjectileActor extends Actor {

  private static Mesh MESH = MeshLoader.loadMesh("Projectile-Mk2.obj");
  
  private static final double MASS = 0.002;
  public static final double SPEED = 0.001;

  public static final int DAMAGE = 100;

  public ProjectileActor(Vector2D position, Rotation rotation, double mass, int id) {
    super(position, rotation, mass, MESH, id);
  }
  
  public ProjectileActor(Vector2D position, Vector2D direction, Vector2D shipVelocity) {
    super(position, Rotation.XRotFromvector(direction), MASS, MESH);
    this.velocity._set(shipVelocity.add(direction._mult(SPEED)));
  }
}