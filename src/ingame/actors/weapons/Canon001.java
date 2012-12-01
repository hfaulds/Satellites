package ingame.actors.weapons;

import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class Canon001 extends Weapon {

  public static final Mesh MESH = MeshLoader.loadMesh("Canon-Mk2.obj");
  
  public Canon001(Vector2D position, Vector2D mountPoint, Rotation shipRotation) {
    super(position, mountPoint, shipRotation, MESH);
  }
  
  @Override
  public String getName() {
    return "Canon";
  }
}
