package net.client;

import net.NetworkListener;
import net.msg.ActorCreateMsg;
import net.msg.ActorUpdateMsg;
import net.msg.ChatMsg;
import net.msg.SceneCreateMsg;
import scene.Scene;
import scene.actors.Actor;
import scene.actors.ProjectileActor;

import com.esotericsoftware.kryonet.Connection;

public class ClientListener extends NetworkListener {

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
    
    if(info instanceof SceneCreateMsg) {
      SceneCreateMsg sceneInfo = (SceneCreateMsg)info;
      this.connection = connection;
      scene.populate(sceneInfo.actorInfoList, sceneInfo.playerID, connection);
      
    } else if(info instanceof ActorUpdateMsg) {
      ActorUpdateMsg actorInfo = (ActorUpdateMsg) info;
      Actor actor = scene.findActor(actorInfo.id);
      
      if(actor != null) {
        actor._update(actorInfo);
      }
      
      
    }else if(info instanceof ActorCreateMsg) {
      ActorCreateMsg actorInfo = (ActorCreateMsg) info;
      if(actorInfo.actorClass.equals(ProjectileActor.class)) {
        scene.queueAddActor(Actor.fromInfo(actorInfo));
      }
      
    } else if(info instanceof ChatMsg) {
      scene.messageHandler.displayMessage((ChatMsg) info);
    }
  }
  
  public boolean isConnected() {
    return connection != null;
  }
}
