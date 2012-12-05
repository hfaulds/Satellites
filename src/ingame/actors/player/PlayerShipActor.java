package ingame.actors.player;

import ingame.actors.ShipActor;
import ingame.actors.StationActor;
import ingame.actors.weapons.WeaponActor;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.net.msg.ActorCreateMsg;

public class PlayerShipActor extends ShipActor {

  public static final Vector2D START_DIRECTION = new Vector2D(0, 1);

  private WeaponActor currentWeapon = weapons.get(0);
  private final Vector2D aimDirection = START_DIRECTION;
  
  public PlayerShipActor(ShipActor player) {
    super(player.position, player.rotation, player.mass, player.id);
    this.add(new PlayerDirection(this));
    this.add(new PlayerHealth(this));
    this.add(new PlayerAim(this));
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

  public WeaponActor getCurrentWeapon() {
    return currentWeapon ;
  }

  public void setAimDirection(Vector2D direction) {
    this.aimDirection._set(direction);
    for(WeaponActor weapon : this.weapons) {
      weapon.rotation.mag = Rotation.XRotFromVector(direction).mag;
    }
  }

  public Vector2D getAimDirection() {
    return this.aimDirection;
  }

  @Override
  public ActorCreateMsg getCreateMsg() {
    return new ActorCreateMsg(position, rotation, id, mass, ShipActor.class);
  }
  

}
