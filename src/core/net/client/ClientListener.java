package core.net.client;

import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import core.Scene;
import core.net.msg.MsgListener;
import core.net.msg.pregame.LoginMsg;

public class ClientListener extends Listener {

  private final List<MsgListener> listeners = new LinkedList<MsgListener>();
  
  private final Scene scene;
  private final LoginMsg login;
  
  public ClientListener(Scene scene, LoginMsg login) {
    this.scene = scene;
    this.login = login;
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
        if(listener.handlesMsg(info)) {
          listener.msgReceived(info, connection);
        }
      }
    }
  }
  
  @Override
  public void connected(Connection connection) {
    connection.sendTCP(login);
  }
  
  @Override
  public void disconnected(Connection connection) {
    scene.actors.clear();
    scene.controllers.clear();
  }
  
}
