package ingame.actors.ship;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Vector2D;

public class Canon extends Actor {

  public static final Mesh MESH = MeshLoader.loadMesh("Canon-Mk2.obj");

  public Canon(Actor parent) {
    super(parent, new Vector2D(), parent.rotation, 20, MESH);
  }

}
