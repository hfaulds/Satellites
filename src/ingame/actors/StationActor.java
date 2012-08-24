package ingame.actors;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class StationActor extends Actor {
  
  private static final double MASS = 0.1;
  private static final Mesh MESH   = MeshLoader.loadMesh("Station-Mk2.obj");
  
  public StationActor(Vector2D position, Rotation rotation, double mass, boolean visible, int id, int owner) {
    super(position, rotation, mass, MESH, visible, id, owner);
    add(new StationShieldActor(this));
  }
  
  public StationActor(double x, double y) {
    super(new Vector2D(x,y), new Rotation(), MASS, MESH);
    add(new StationShieldActor(this));
  }
  
}
