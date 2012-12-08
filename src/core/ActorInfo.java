package core;

import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ActorInfo {
  
  private static int ID_COUNT = 1;
  
  public Vector2D position;
  public Rotation rotation;
  public double mass;
  public Mesh mesh;
  public int id;
  
  public ActorInfo(Vector2D position, Rotation rotation, double mass, Mesh mesh) {
    this(position, rotation, mass, mesh, NEXT_ID());
  }

  public ActorInfo(Vector2D position, Rotation rotation, double mass, Mesh mesh, int id) {
    this.position = position;
    this.rotation = rotation;
    this.mass = mass;
    this.mesh = mesh;
    this.id = id;
    INCREMENT_TOTAL_ACTORS_BEYOND(id);
  }
  
  /* IDs */
  
  private static int NEXT_ID() {
    return ID_COUNT + 1;
  }

  private static void INCREMENT_TOTAL_ACTORS_BEYOND(int id) {
    ID_COUNT = Math.max(ID_COUNT, id);
  }
}