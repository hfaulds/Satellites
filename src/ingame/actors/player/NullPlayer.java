package ingame.actors.player;

import ingame.actors.StationActor;
import ingame.actors.weapons.NullWeapon;
import ingame.actors.weapons.Weapon;
import core.geometry.Vector2D;

public class NullPlayer extends PlayerShipActor {

  @Override
  public void dock(StationActor station) {
  }

  @Override
  public void undock(StationActor station) {
  }

  @Override
  public Weapon getCurrentWeapon() {
    return new NullWeapon();
  }

  @Override
  public void setAimDirection(Vector2D direction) {
  }
}