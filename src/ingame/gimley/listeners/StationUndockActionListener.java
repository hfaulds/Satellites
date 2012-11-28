package ingame.gimley.listeners;

import ingame.actors.PlayerShipActor;
import ingame.gimley.components.StationDisplay;
import core.gimley.actions.ActionEvent;
import core.gimley.listeners.ActionListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ShipDockMsg;

public class StationUndockActionListener implements ActionListener {
  
  private final StationDisplay stationDisplay;
  private final NetworkConnection connection;
  private final PlayerShipActor player;

  public StationUndockActionListener(StationDisplay stationDisplay,
      NetworkConnection connection, PlayerShipActor player) {
    this.stationDisplay = stationDisplay;
    this.connection = connection;
    this.player = player;
  }

  @Override
  public void action(ActionEvent action) {
    connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.UNDOCKING));

    stationDisplay.setVisible(false);
    
    player.undock(stationDisplay.getStation());
  }
}