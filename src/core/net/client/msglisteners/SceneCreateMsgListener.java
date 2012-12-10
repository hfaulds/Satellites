package core.net.client.msglisteners;

import com.esotericsoftware.kryonet.Connection;

import core.Scene;
import core.net.msg.MsgListener;
import core.net.msg.pregame.SceneCreateMsg;

public class SceneCreateMsgListener implements MsgListener {
  
  private final Scene scene;

  public SceneCreateMsgListener(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void msgReceived(Object msg, Connection connection) {
    SceneCreateMsg sceneInfo = (SceneCreateMsg)msg;
    scene.populate(sceneInfo, connection);
  }

  @Override
  public boolean handlesMsg(Object info) {
    return info instanceof SceneCreateMsg;
  }
}