package net;

import net.msg.ChatMsg;

public class NoConnection extends NetworkConnection {

  @Override
  public void disconnect() {}

  @Override
  public boolean isOnline() {
    return false;
  }

  @Override
  public void sendMessage(ChatMsg message) {}

}
