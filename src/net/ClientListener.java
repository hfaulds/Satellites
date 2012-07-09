package net;

import java.util.List;

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
  public void connected(Connection connection) {
    
  }
  
  @Override
  public void disconnected(Connection connection) {
    
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof List<?>) {
      scene.addActors((List<ActorInfo>)info);
    } else if(info instanceof ActorInfo) {
      ActorInfo actorInfo = (ActorInfo) info;
      Actor actor = scene.findActor(actorInfo.id);
      System.out.println(actorInfo.id);
      if(actor != null) {
        actor._update(actorInfo);
      }
    }
  }
}
