package net;

import scene.Scene;
import actors.Actor;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {

  private final Scene scene;
  
  public ClientListener(Scene scene) {
    this.scene = scene;
  }
  
  @Override
  public void disconnected(Connection connection) {
    
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof SceneInfo) {
      SceneInfo sceneInfo = (SceneInfo)info;
      scene.populate(sceneInfo.actorInfoList, sceneInfo.player, connection);
    } else if(info instanceof ActorInfo) {
      ActorInfo actorInfo = (ActorInfo) info;
      Actor actor = scene.findActor(actorInfo.id);
      
      if(actor != null) {
        actor._update(actorInfo);
      }
    }
  }
}
