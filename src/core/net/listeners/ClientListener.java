package core.net.listeners;

import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import core.Scene;
import core.net.msg.MsgListener;

public class ClientListener extends Listener {

  private final List<MsgListener> listeners = new LinkedList<MsgListener>();
  
  private final Scene scene;
  
  public ClientListener(final Scene scene) {
    this.scene = scene;
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
    System.out.println("Client connected");
  }
  
  @Override
  public void disconnected(Connection connection) {
    scene.actors.clear();
    scene.controllers.clear();
  }
  
}
