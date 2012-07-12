package net.server;

import java.util.List;

import net.msg.ActorMsg;
import net.msg.ChatMsg;
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
    PlayerConnection clientConnection = (PlayerConnection)connection;
    
    scene.addActor(clientConnection.actor);
    scene.addController(clientConnection.controller);

    List<ActorMsg> actorInfoList = ActorMsg.actorInfoList(scene.actors);
    connection.sendTCP(new SceneMsg(actorInfoList, clientConnection.actor.id));
  }
  
  @Override
  public void disconnected(Connection connection) {
    PlayerConnection clientConnection = (PlayerConnection)connection;
    scene.removeController(clientConnection.controller);
    scene.removeActor(clientConnection.actor);
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof PlayerMsg) {
      ((PlayerConnection)connection).updateActor((PlayerMsg)info);
    } else if(info instanceof ChatMsg) {
      scene.messageHandler.addMessage((ChatMsg) info);
    }
  }
}
