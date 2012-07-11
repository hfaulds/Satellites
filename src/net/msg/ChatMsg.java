package net.msg;

import net.ClientConnection;

public class ChatMsg {
  
  public final String message;
  public final ClientConnection sender;
  
  public ChatMsg(String message, ClientConnection sender) {
    this.message = message;
    this.sender = sender;
  }
}
