package ingame.gimley.listeners;

import ingame.actors.StationActor;
import ingame.gimley.components.StationDisplay;
import ingame.gimley.components.StationDockRequest;
import core.Actor;
import core.Scene;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.gimley.actions.ActionEvent;
import core.gimley.listeners.ActionListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ShipDockMsg;

public class StationDockActionListener implements ActionListener {

  private final Scene scene;
  private final StationDockRequest stationDockRequest;
  private final NetworkConnection connection;
  private final StationDisplay stationDisplay;

  public StationDockActionListener(Scene scene, StationDockRequest stationDockRequest, NetworkConnection connection, StationDisplay stationDisplay) {
    this.scene = scene;
    this.stationDockRequest = stationDockRequest;
    this.connection = connection;
    this.stationDisplay = stationDisplay;
  }

  @Override
  public void action(ActionEvent action) {
    scene.input.setActor(null);
    
    StationActor station = stationDisplay.getStation();

    Actor player = scene.player;
    player.velocity._set(new Vector2D());
    player.spin._set(new Rotation());
    player.position._set(station.position.add(new Vector2D(20, 0)));
    player.setVisible(false);
    
    connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.DOCKING));
    
    stationDockRequest.setVisible(false);
    stationDisplay.setVisible(true);
  }
}