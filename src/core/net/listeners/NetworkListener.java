package core.net.listeners;

import java.util.LinkedList;
import java.util.List;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import core.net.MsgListener;

public class NetworkListener extends Listener {

  protected final List<MsgListener> listeners = new LinkedList<MsgListener>();
  
  public void addMsgListener(MsgListener listener) {
    synchronized(listeners) {
      listeners.add(listener);
    }
  }
  
  @Override
  public void received(Connection connection, Object info) {
    synchronized(listeners) {
      for(MsgListener listener : listeners) {
        if(info.getClass().equals( listener.getMsgClass())) {
          listener.msgReceived(info, connection);
        }
      }
    }
  }
}
