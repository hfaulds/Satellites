package ingame.gimley.listeners;

import ingame.actors.StationActor;
import ingame.actors.player.PlayerShipActor;
import ingame.gimley.components.StationDisplay;
import ingame.gimley.components.StationDockRequest;
import core.gimley.actions.ActionEvent;
import core.gimley.listeners.ActionListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ShipDockMsg;

public class StationDockActionListener implements ActionListener {

  private final PlayerShipActor player;
  private final StationDockRequest stationDockRequest;
  private final NetworkConnection connection;
  private final StationDisplay stationDisplay;

  public StationDockActionListener(PlayerShipActor player, StationDockRequest stationDockRequest, NetworkConnection connection, StationDisplay stationDisplay) {
    this.player = player;
    this.stationDockRequest = stationDockRequest;
    this.connection = connection;
    this.stationDisplay = stationDisplay;
  }

  @Override
  public void action(ActionEvent action) {

    StationActor station = stationDisplay.getStation();
    player.dock(station);

    connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.DOCKING));
    
    stationDockRequest.setVisible(false);
    stationDisplay.setVisible(true);
  }
}