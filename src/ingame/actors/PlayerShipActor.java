package ingame.actors;

import core.geometry.Vector2D;
import ingame.actors.ship.ShipAim;
import ingame.actors.ship.ShipControl;
import ingame.actors.ship.ShipDirection;
import ingame.actors.ship.ShipHealth;
import ingame.controllers.PlayerInputController;

public class PlayerShipActor extends ShipActor {

  public PlayerShipActor(ShipActor player, PlayerInputController input) {
    super(player.position, player.rotation, player.mass, player.id);
    this.add(new ShipControl(this));
    this.add(new ShipDirection(this));
    this.add(new ShipHealth(this));
    this.add(new ShipAim(this, input));
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

}
