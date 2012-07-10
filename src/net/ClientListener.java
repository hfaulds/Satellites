package net;

import scene.Scene;
import actors.Actor;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import controllers.client.ClientShipController;

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
      
      scene.player.id = sceneInfo.playerID;
      scene.addController(new ClientShipController(scene.player, connection));

      scene.addActor(scene.player);
      scene.populate(sceneInfo.actorInfoList);
    } else if(info instanceof ActorInfo) {
      ActorInfo actorInfo = (ActorInfo) info;
      Actor actor = scene.findActor(actorInfo.id);
      
      if(actor != null) {
        actor._update(actorInfo);
      }
    }
  }
}
