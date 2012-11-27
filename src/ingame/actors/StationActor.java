package ingame.actors;

import core.Actor;
import core.ActorInfo;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class StationActor extends Actor {
  
  private static final double MASS = 1000;
  private static final Mesh MESH   = MeshLoader.loadMesh("Station-Mk2.obj");
  
  public StationActor(Vector2D position, Rotation rotation, double mass, int id) {
    super(new ActorInfo(position, rotation, mass, MESH, id));
    add(new StationShieldActor(this));
  }
  
  public StationActor(double x, double y) {
    super(new ActorInfo(new Vector2D(x,y), new Rotation(), MASS, MESH));
    add(new StationShieldActor(this));
  }
  
}
