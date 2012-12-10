package core.net.server;

import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import core.Scene;
import core.net.msg.MsgListener;

public class ServerListener extends Listener {

  private final Scene scene;
  
  private final List<MsgListener> listeners = new LinkedList<MsgListener>();
  private final List<MsgListener> pregameListeners = new LinkedList<MsgListener>();
  
  public ServerListener(Scene scene) {
    this.scene = scene;
  }

  public void addMsgListener(MsgListener listener) {
    synchronized(listeners) {
      listeners.add(listener);
    }
  }
  
  public void addPregameMsgListener(MsgListener listener) {
    synchronized(pregameListeners) {
      pregameListeners.add(listener);
    }
  }
  
  @Override
  public void received(Connection connection, Object info) {
    PlayerConnection player = (PlayerConnection)connection;
    
    synchronized(listeners) {
      if(player.isAuthenticated()) {
        callListeners(connection, info, listeners);
      } else {
        callListeners(connection, info, pregameListeners);
      }
    }
  }

  private void callListeners(Connection connection, Object info, List<MsgListener> listeners) {
    for(MsgListener listener : listeners) {
      if(listener.handlesMsg(info)) {
        listener.msgReceived(info, connection);
      }
    }
  }
  
  @Override
  public void disconnected(Connection connection) {
    PlayerConnection clientConnection = (PlayerConnection)connection;
    scene.removeActor(clientConnection.getActor());
    scene.removeController(clientConnection.getController());
  }
 
}
