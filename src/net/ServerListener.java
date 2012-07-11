package net;

import java.util.List;

import scene.Scene;

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
    
    ClientConnection clientConnection = (ClientConnection)connection;
    scene.addController(clientConnection.controller);
    scene.addActor(clientConnection.actor);
    
    connection.sendTCP(new SceneInfo(actorInfoList, clientConnection.actor.getInfo()));
  }
  
  @Override
  public void disconnected(Connection connection) {
    ClientConnection clientConnection = (ClientConnection)connection;
    scene.removeController(clientConnection.controller);
    scene.removeActor(clientConnection.actor);
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof PlayerInfo) {
      ((ClientConnection)connection).updateActor((PlayerInfo)info);
    }
  }
}
