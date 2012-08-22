package net.listeners;

import net.msg.MsgListener;
import net.msg.SceneCreateMsg;
import scene.Scene;

import com.esotericsoftware.kryonet.Connection;

public class ClientListener extends NetworkListener {

  private final Scene scene;
  public Connection connection;
  
  public ClientListener(final Scene scene) {
    this.scene = scene;
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection connection) {
        SceneCreateMsg sceneInfo = (SceneCreateMsg)msg;
        ClientListener.this.connection = connection;
        scene.populate(sceneInfo.actorInfoList, sceneInfo.playerID, connection);
      }

      @Override
      public Class<?> getMsgClass() {
        return SceneCreateMsg.class;
      }
    });
  }
  
  @Override
  public void disconnected(Connection connection) {
    scene.actors.clear();
    scene.controllers.clear();
    this.connection = null;
  }
  
  public boolean isConnected() {
    return connection != null;
  }
}
