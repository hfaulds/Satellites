package net.client;

import net.msg.ActorMsg;
import net.msg.ChatMsg;
import net.msg.SceneMsg;
import scene.Scene;
import actors.Actor;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {

  private final Scene scene;
  public Connection connection;
  
  public ClientListener(Scene scene) {
    this.scene = scene;
  }
  
  @Override
  public void disconnected(Connection connection) {
    scene.actors.clear();
    scene.controllers.clear();
    this.connection = null;
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof SceneMsg) {
      SceneMsg sceneInfo = (SceneMsg)info;
      this.connection = connection;
      scene.populate(sceneInfo.actorInfoList, sceneInfo.playerID, connection);
      
    } else if(info instanceof ActorMsg) {
      ActorMsg actorInfo = (ActorMsg) info;
      Actor actor = scene.findActor(actorInfo.id);
      
      if(actor != null) {
        actor._update(actorInfo);
      }
    } else if(info instanceof ChatMsg) {
      scene.messageHandler.addMessage((ChatMsg) info);
    }
  }
  
  public boolean isConnected() {
    return connection != null;
  }
}
