package net.server;

import java.util.List;

import net.msg.ActorCreateMsg;
import net.msg.ChatMsg;
import net.msg.PlayerMsg;
import net.msg.SceneMsg;
import scene.Scene;
import scene.actors.Actor;
import scene.actors.ProjectileActor;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {

  private final Scene scene;
  
  public ServerListener(Scene scene) {
    this.scene = scene;
  }
  
  @Override
  public void connected(Connection connection) {
    Player player = (Player)connection;
    
    scene.queueAddActor(player.actor);
    scene.addController(player.controller);

    List<ActorCreateMsg> actorInfoList = ActorCreateMsg.actorInfoList(scene);
    connection.sendTCP(new SceneMsg(actorInfoList, player.actor.id));
  }
  
  @Override
  public void disconnected(Connection connection) {
    Player clientConnection = (Player)connection;
    scene.removeController(clientConnection.controller);
    scene.removeActor(clientConnection.actor);
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof PlayerMsg) {
      ((Player)connection).updateActor((PlayerMsg)info);
    } else if(info instanceof ChatMsg) {
      scene.messageHandler.displayMessage((ChatMsg) info);
    } else if(info instanceof ActorCreateMsg) {
      ActorCreateMsg msg = (ActorCreateMsg) info;
      if(msg.actorClass.equals(ProjectileActor.class)) {
        scene.queueAddActor(Actor.fromInfo(msg));
      }
    }
  }
}
