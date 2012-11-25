package ingame.actors;

import ingame.actors.ship.ShipAim;
import ingame.actors.ship.ShipControl;
import ingame.actors.ship.ShipDirection;
import ingame.actors.ship.ShipHealth;
import ingame.controllers.PlayerInputController;

public class PlayerShipActor extends ShipActor {

  public PlayerShipActor(ShipActor player, PlayerInputController input) {
    super(player.position, player.rotation, player.mass, player.health);
    this.add(new ShipControl(this));
    this.add(new ShipDirection(this));
    this.add(new ShipHealth(this));
    this.add(new ShipAim(this, input));
  }

}
