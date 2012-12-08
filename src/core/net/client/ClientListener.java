package core.net.client;

import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import core.Scene;
import core.db.User;
import core.net.msg.MsgListener;
import core.net.msg.pregame.LoginMsg;

public class ClientListener extends Listener {

  private final List<MsgListener> listeners = new LinkedList<MsgListener>();
  
  private final Scene scene;
  private final User user;
  
  public ClientListener(Scene scene, User user) {
    this.scene = scene;
    this.user = user;
  }
  
  public void addMsgListener(MsgListener listener) {
    synchronized(listeners) {
      listeners.add(listener);
    }
  }
  
  @Override
  public void received(Connection connection, Object info) {
    synchronized(listeners) {
      for(MsgListener listener : listeners) {
        if(info.getClass().equals(listener.getMsgClass())) {
          listener.msgReceived(info, connection);
        }
      }
    }
  }
  
  @Override
  public void connected(Connection connection) {
    connection.sendTCP(new LoginMsg(user));
  }
  
  @Override
  public void disconnected(Connection connection) {
    scene.actors.clear();
    scene.controllers.clear();
  }
  
}
