package net;

import java.util.List;

import scene.Scene;
import actors.Actor;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {

  private final Scene scene;
  
  public ServerListener(Scene scene) {
    this.scene = scene;
  }
  
  @Override
  public void connected(Connection connection) {
    List<ActorInfo> actorInfoList = ActorInfo.actorInfoList(scene.actors);
    connection.sendTCP(actorInfoList);
    
    Actor actor = ((ClientConnection)connection).actor;
    scene.addActor(actor);
  }
  
  @Override
  public void disconnected(Connection connection) {
    Actor actor = ((ClientConnection)connection).actor;
    scene.removeActor(actor);
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof PlayerInfo) {
      ((ClientConnection)connection).updateActor((PlayerInfo)info);
    }
  }
}
