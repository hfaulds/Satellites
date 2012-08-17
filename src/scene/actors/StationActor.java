package scene.actors;

import geometry.Mesh;
import geometry.MeshLoader;
import geometry.Rotation;
import geometry.Vector2D;

public class StationActor extends Actor {
  
  private static double MASS   = 0.1;
  
  public static final Mesh MESH = MeshLoader.loadMesh("Station-Mk2.obj");

  public StationActor(Vector2D position, Rotation rotation, double mass, int id) {
    super(position, rotation, mass, MESH, id);
  }

  public StationActor(double x, double y) {
    super(new Vector2D(x,y), new Rotation(), MASS, MESH);
  }

}
