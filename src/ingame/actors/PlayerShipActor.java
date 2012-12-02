package ingame.actors;

import ingame.actors.ship.ShipAim;
import ingame.actors.ship.ShipControl;
import ingame.actors.ship.ShipDirection;
import ingame.actors.ship.ShipHealth;
import ingame.actors.weapons.Weapon;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class PlayerShipActor extends ShipActor {

  public static final Vector2D START_DIRECTION = new Vector2D(0, 1);

  private Weapon currentWeapon = weapons.get(0);
  
  private final Vector2D aimDirection = START_DIRECTION;
  
  public PlayerShipActor(ShipActor player) {
    super(player.position, player.rotation, player.mass, player.id);
    this.add(new ShipControl(this));
    this.add(new ShipDirection(this));
    this.add(new ShipHealth(this));
    this.add(new ShipAim(this));
  }
  
  protected PlayerShipActor() {
    super(0, 0);
  }

  public void dock(StationActor station) {
    this.freeze();
    this.setVisible(false);
  }

  public void undock(StationActor station) {
    this.position._set(station.position.add(new Vector2D(20, 0)));
    this.setVisible(true);
    this.unFreeze();
  }

  public Weapon getCurrentWeapon() {
    return currentWeapon ;
  }

  public void setAimDirection(Vector2D direction) {
    this.aimDirection._set(direction);
    for(Weapon weapon : this.weapons) {
      weapon.rotation.mag = Rotation.XRotFromVector(direction).mag;
    }
  }

  public Vector2D getAimDirection() {
    return this.aimDirection;
  }

}
