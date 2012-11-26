package ingame.gimley.listeners;

import ingame.actors.PlayerShipActor;
import ingame.gimley.components.StationDisplay;
import core.Scene;
import core.gimley.actions.ActionEvent;
import core.gimley.listeners.ActionListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ShipDockMsg;

public class StationUndockActionListener implements ActionListener {
  
  private final StationDisplay stationDisplay;
  private final NetworkConnection connection;
  private final Scene scene;

  public StationUndockActionListener(StationDisplay stationDisplay,
      NetworkConnection connection, Scene scene) {
    this.stationDisplay = stationDisplay;
    this.connection = connection;
    this.scene = scene;
  }

  @Override
  public void action(ActionEvent action) {
    PlayerShipActor player = scene.player;
    connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.UNDOCKING));

    stationDisplay.setVisible(false);
    
    player.undock(stationDisplay.getStation());
  }
}