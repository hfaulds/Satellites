package ingame.actors;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ProjectileActor extends Actor {

  private static Mesh MESH = MeshLoader.loadMesh("Projectile-Mk2.obj");
  
  private static final double MASS = 0.002;
  public static final double SPEED = 0.001;

  public static final int DAMAGE = 100;

  public ProjectileActor(Vector2D position, Rotation rotation, double mass, boolean visible, int id, int owner) {
    super(position, rotation, mass, MESH, visible, id, owner);
  }
  
  public ProjectileActor(Vector2D position, Vector2D direction, Vector2D shipVelocity, Actor owner) {
    super(position, Rotation.XRotFromvector(direction), MASS, MESH, owner.id);
    this.velocity._set(shipVelocity.add(direction._mult(SPEED)));
  }
}