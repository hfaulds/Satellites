package core.net.listeners;


import com.esotericsoftware.kryonet.Connection;

import core.Scene;
import core.net.MsgListener;
import core.net.msg.SceneCreateMsg;

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
        scene.populate(sceneInfo, connection);
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
