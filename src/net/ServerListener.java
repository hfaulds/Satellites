package net;

import java.util.List;

import scene.Scene;
import actors.Actor;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import controllers.server.ServerPlayerController;

public class ServerListener extends Listener {

  private final Server server;
  private final Scene scene;
  
  public ServerListener(Scene scene, Server server) {
    this.scene = scene;
    this.server = server;
  }
  
  @Override
  public void connected(Connection connection) {
    List<ActorInfo> actorInfoList = ActorInfo.actorInfoList(scene.actors);
    
    Actor actor = ((ClientConnection)connection).actor;
    scene.addController(new ServerPlayerController(actor, server));
    scene.addActor(actor);
    
    connection.sendTCP(new SceneInfo(actorInfoList, actor.id));
  }
  
  @Override
  public void disconnected(Connection connection) {
    Actor actor = ((ClientConnection)connection).actor;
    scene.removeActor(actor);
  }
  
  @Override
  public void received(Connection connection, Object info) {
    if(info instanceof PlayerInfo) {
      PlayerInfo playerInfo = (PlayerInfo)info;
      ((ClientConnection)connection).updateActor(playerInfo);
    }
  }
}
