package scene.actors;

import geometry.Mesh;
import geometry.MeshLoader;
import geometry.Rotation;
import geometry.Vector2D;

public class Planet1Actor extends Actor {

  public static final int MASS = 10;
  private static Mesh MESH = MeshLoader.loadMesh("Planet 1000.obj");  

  public Planet1Actor(Vector2D position, Rotation rotation, double mass, int id) {
    super(position, rotation, mass, MESH, id);
  }
  
  public Planet1Actor(double x, double y) {
    super(new Vector2D(x,y), new Rotation(), MASS, MESH);
  }

}
