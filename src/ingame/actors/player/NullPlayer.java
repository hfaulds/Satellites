package ingame.actors.player;

import ingame.actors.ShipActor;
import ingame.actors.StationActor;
import ingame.actors.weapons.NullWeapon;
import ingame.actors.weapons.WeaponActor;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class NullPlayer extends PlayerShipActor {

  public NullPlayer() {
    super(new ShipActor(new Vector2D(), new Rotation(), 0, 0, ""));
  }

  @Override
  public void dock(StationActor station) {
  }

  @Override
  public void undock(StationActor station) {
  }

  @Override
  public WeaponActor getCurrentWeapon() {
    return new NullWeapon();
  }

  @Override
  public void setAimDirection(Vector2D direction) {
  }
}