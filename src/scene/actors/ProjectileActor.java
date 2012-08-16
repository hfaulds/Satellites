package scene.actors;

import geometry.Mesh;
import geometry.MeshLoader;
import geometry.Rotation;
import geometry.Vector2D;

public class ProjectileActor extends Actor {

  private static Mesh MESH = MeshLoader.loadMesh("Projectile-Mk2.obj");
  
  private static final double MASS = 0.002;
  private static final double SPEED = 0.00001;

  public ProjectileActor(Vector2D position, Rotation rotation, double mass, int id) {
    super(position, rotation, mass, MESH, id);
  }
  
  public ProjectileActor(Vector2D position, Vector2D direction) {
    super(position.x, position.y, MASS, MESH);
    this.velocity._set(direction._mult(SPEED));
  }
}
