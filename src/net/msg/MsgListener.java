package net.msg;

import com.esotericsoftware.kryonet.Connection;

public interface MsgListener {
  public void msgReceived(Object msg, Connection connection);
  public Class<?> getMsgClass();
}
