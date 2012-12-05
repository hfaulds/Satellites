package ingame.actors.weapons;

import ingame.items.AmmoItem;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class Canon001 extends WeaponActor {

  public static final long COOLDOWN = 1000;
  public static final Mesh MESH = MeshLoader.loadMesh("Canon-Mk2.obj");
  
  public Canon001(Vector2D position, Vector2D mountPoint, Rotation shipRotation, AmmoItem ammo) {
    super(position, mountPoint, shipRotation, MESH, ammo);
  }
  
  @Override
  public String getName() {
    return "Canon";
  }

  @Override
  public long getCoolDown() {
    return COOLDOWN;
  }
 
}
