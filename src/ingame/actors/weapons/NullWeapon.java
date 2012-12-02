package ingame.actors.weapons;

import ingame.controllers.NullAmmoItem;
import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class NullWeapon extends Weapon {

  public NullWeapon() {
    super(new Vector2D(), new Vector2D(), new Rotation(), new Mesh(), new NullAmmoItem());
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public long getCoolDown() {
    return 0;
  }

}
