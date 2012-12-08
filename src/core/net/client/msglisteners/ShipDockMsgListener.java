package core.net.client.msglisteners;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ShipDockMsg;

public class ShipDockMsgListener implements MsgListener {
  
  private final Scene scene;

  public ShipDockMsgListener(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void msgReceived(Object msg, Connection connection) {
    ShipDockMsg dockInfo = (ShipDockMsg)msg;
    Actor ship = scene.findActor(dockInfo.id);
    if(dockInfo.status == ShipDockMsg.DOCKING) {
      ship.setVisible(false);
    } else {
      ship.setVisible(true);
    }
  }

  @Override
  public Class<?> getMsgClass() {
    return ShipDockMsg.class;
  }
}