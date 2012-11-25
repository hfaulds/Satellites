package ingame.collisions;

import ingame.actors.PlayerShipActor;
import ingame.actors.StationShieldActor;
import ingame.gimley.components.StationDisplay;
import ingame.gimley.components.StationDockRequest;
import core.collisions.ClassPair;
import core.collisions.Collision;
import core.collisions.CollisionListener;

public class ShipStationShieldCollisionListener extends CollisionListener {
  
  private final StationDisplay stationDisplay;
  private final StationDockRequest stationDockRequest;

  public ShipStationShieldCollisionListener(StationDisplay stationDisplay, StationDockRequest stationDockRequest) {
    super(new ClassPair(PlayerShipActor.class, StationShieldActor.class));
    this.stationDisplay = stationDisplay;
    this.stationDockRequest = stationDockRequest;
  }

  @Override
  public void collisionStart(Collision collision) {
      stationDockRequest.setVisible(true);
      stationDisplay.setStation(((StationShieldActor)collision.b).station);
  }

  @Override
  public void collisionEnd(Collision collision) {
      stationDockRequest.setVisible(false);
  }
}