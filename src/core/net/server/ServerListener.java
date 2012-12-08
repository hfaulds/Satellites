package core.net.server;

import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import core.Scene;
import core.db.User;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.ingame.SceneCreateMsg;
import core.net.msg.pregame.LoginMsg;

public class ServerListener extends Listener {

  private final Scene scene;
  private final List<MsgListener> listeners = new LinkedList<MsgListener>();
  
  public ServerListener(Scene scene) {
    this.scene = scene;
  }

  public void addMsgListener(MsgListener listener) {
    synchronized(listeners) {
      listeners.add(listener);
    }
  }
  
  @Override
  public void received(Connection connection, Object info) {
    PlayerConnection player = (PlayerConnection)connection;
    
    synchronized(listeners) {

      if(player.isAuthenticated()) {
        for(MsgListener listener : listeners) {
          if(info.getClass().equals(listener.getMsgClass())) {
            listener.msgReceived(info, connection);
          }
        }
      } else if(info instanceof LoginMsg) {
        LoginMsg msg = (LoginMsg) info;
        String username = msg.username;
        User user = User.findByUsername(username);
        if(user != null) {
          authenticatePlayer(player);
        }
      }
      
    }
  }
  
  
  public void authenticatePlayer(PlayerConnection player) {
    player.setAuthenticated(true);
    scene.queueAddActor(player.actor);
    scene.addController(player.controller);

    List<ActorCreateMsg> actorInfoList = ActorCreateMsg.actorInfoList(scene);
    player.sendTCP(new SceneCreateMsg(actorInfoList, player.actor.id));
  }
  
  @Override
  public void disconnected(Connection connection) {
    PlayerConnection clientConnection = (PlayerConnection)connection;
    scene.removeController(clientConnection.controller);
    scene.removeActor(clientConnection.actor);
  }
 
}
