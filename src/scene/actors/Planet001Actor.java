package scene.actors;

import scene.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class Planet001Actor extends Actor {

  public static final int MASS = 10;
  private static Mesh MESH = MeshLoader.loadMesh("Planet 1000.obj");  

  public Planet001Actor(Vector2D position, Rotation rotation, double mass, boolean visible, int id, int owner) {
    super(position, rotation, mass, MESH, visible, id, owner);
  }
  
  public Planet001Actor(double x, double y) {
    super(new Vector2D(x,y), new Rotation(), MASS, MESH);
  }

}
