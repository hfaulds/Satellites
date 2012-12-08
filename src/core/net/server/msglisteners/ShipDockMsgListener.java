package core.net.server.msglisteners;

import com.esotericsoftware.kryonet.Connection;

import core.net.msg.MsgListener;
import core.net.msg.ingame.ShipDockMsg;
import core.net.server.ServerConnection;

public class ShipDockMsgListener implements MsgListener {

  private final ServerConnection serverConnection;

  public ShipDockMsgListener(ServerConnection serverConnection) {
    this.serverConnection = serverConnection;
  }

  @Override
  public void msgReceived(Object msg, Connection connection) {
    this.serverConnection.sendMsg((ShipDockMsg) msg);
  }

  @Override
  public Class<?> getMsgClass() {
    return ShipDockMsg.class;
  }
}