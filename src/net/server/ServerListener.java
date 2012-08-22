package net.server;

import java.util.List;

import net.NetworkListener;
import net.msg.ActorCreateMsg;
import net.msg.SceneCreateMsg;
import scene.Scene;

import com.esotericsoftware.kryonet.Connection;

public class ServerListener extends NetworkListener {

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
    connection.sendTCP(new SceneCreateMsg(actorInfoList, player.actor.id));
  }
  
  @Override
  public void disconnected(Connection connection) {
    Player clientConnection = (Player)connection;
    scene.removeController(clientConnection.controller);
    scene.removeActor(clientConnection.actor);
  }
 
}
