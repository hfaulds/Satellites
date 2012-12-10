package ingame.actors.weapons;

import core.geometry.Rotation;
import core.geometry.Vector2D;

public class NullWeapon extends WeaponActor {

  public NullWeapon() {
    super(new Vector2D(), new Vector2D(), new Rotation(), "", null);
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public long getCoolDown() {
    return 0;
  }
  
  @Override
  public boolean fire() {
    return false;
  }

}