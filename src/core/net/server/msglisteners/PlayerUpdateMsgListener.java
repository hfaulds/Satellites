package core.net.server.msglisteners;

import com.esotericsoftware.kryonet.Connection;

import core.net.msg.MsgListener;
import core.net.msg.ingame.PlayerUpdateMsg;
import core.net.server.PlayerConnection;

public class PlayerUpdateMsgListener implements MsgListener {
  
  @Override
  public void msgReceived(Object msg, Connection connection) {
    PlayerConnection player = (PlayerConnection) connection;
    player.updateActor((PlayerUpdateMsg) msg);
  }

  @Override
  public Class<?> getMsgClass() {
    return PlayerUpdateMsg.class;
  }
}