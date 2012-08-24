package ingame.actors.ship;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;

public class Canon extends Actor {

  public static final Mesh MESH = MeshLoader.loadMesh("Canon-Mk2.obj");

  public Canon(Actor parent) {
    super(parent, parent.position, parent.rotation, 0, MESH);
  }

}
