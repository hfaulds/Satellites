package ingame.collisions;

import ingame.actors.ShipActor;
import ingame.actors.StationShieldActor;
import ingame.gimley.components.StationDisplay;
import ingame.gimley.components.StationDockRequest;
import core.Scene;
import core.collisions.Collision;
import core.collisions.CollisionListener;

public class ShipStationShieldCollisionHandler extends
    CollisionListener {
  private final StationDisplay stationDisplay;
  private final StationDockRequest stationDockRequest;
  private final Scene scene;

  @SuppressWarnings("unchecked")
  public ShipStationShieldCollisionHandler(StationDisplay stationDisplay, StationDockRequest stationDockRequest, Scene scene) {
    super(new Class[]{ShipActor.class, StationShieldActor.class});
    this.stationDisplay = stationDisplay;
    this.stationDockRequest = stationDockRequest;
    this.scene = scene;
  }

  @Override
  public void collisionStart(Collision collision) {
    if(collision.a == scene.player) {
      stationDockRequest.setVisible(true);
      stationDisplay.setStation(((StationShieldActor)collision.b).station);
    }
  }

  @Override
  public void collisionEnd(Collision collision) {
    if(collision.a == scene.player) {
      stationDockRequest.setVisible(false);
    }
  }
}