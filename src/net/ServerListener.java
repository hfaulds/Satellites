package net;

import java.util.List;

import net.msg.ActorMsg;
import net.msg.PlayerMsg;
import net.msg.SceneMsg;

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
    ClientConnection clientConnection = (ClientConnection)connection;
    
    scene.addActor(clientConnection.actor);
    scene.addController(clientConnection.controller);

    List<ActorMsg> actorInfoList = ActorMsg.actorInfoList(scene.actors);
    connection.sendTCP(new SceneMsg(actorInfoList, clientConnection.actor.id));
  }
  
  @Override
  public void disconnected(Connection connection) {
    ClientConnection clientConnection = (ClientConnection)connection;
    scene.removeController(clientConnection.controller);
    scene.removeActor(clientConnection.actor);
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof PlayerMsg) {
      ((ClientConnection)connection).updateActor((PlayerMsg)info);
    }
  }
}
