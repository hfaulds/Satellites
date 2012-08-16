package net.server;

import java.util.List;

import net.msg.ActorCreateMsg;
import net.msg.ChatMsg;
import net.msg.PlayerUpdateMsg;
import net.msg.SceneCreateMsg;
import scene.Scene;
import scene.actors.Actor;
import scene.actors.ProjectileActor;
import scene.controllers.ServerActorController;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerListener extends Listener {

  private final Scene scene;
  private final Server server;
  
  public ServerListener(Scene scene, Server server) {
    this.scene = scene;
    this.server = server;
  }
  
  @Override
  public void connected(Connection connection) {
    Player player = (Player)connection;
    
    scene.queueAddActor(player.actor);
    scene.addController(player.controller);

    List<ActorCreateMsg> actorInfoList = ActorCreateMsg.actorInfoList(scene);
    connection.sendTCP(new SceneCreateMsg(actorInfoList, player.actor.id));
  }
  
  @Override
  public void disconnected(Connection connection) {
    Player clientConnection = (Player)connection;
    scene.removeController(clientConnection.controller);
    scene.removeActor(clientConnection.actor);
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof PlayerUpdateMsg) {
      ((Player)connection).updateActor((PlayerUpdateMsg)info);
    } else if(info instanceof ChatMsg) {
      scene.messageHandler.displayMessage((ChatMsg) info);
    } else if(info instanceof ActorCreateMsg) {
      ActorCreateMsg msg = (ActorCreateMsg) info;
      if(msg.actorClass.equals(ProjectileActor.class)) {
        Actor projectile = Actor.fromInfo(msg);
        scene.queueAddActor(projectile);
        scene.addController(new ServerActorController(projectile, server));
      }
    }
  }
}
