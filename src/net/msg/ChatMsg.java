package net.msg;

public class ChatMsg {
  
  static final int FADE_TIME = 10000;

  public final String owner;
  public final String text;
  public final long sent;

  public ChatMsg() {
    this("");
  }
  
  public ChatMsg(String text) {
    this.owner = "Bob";
    this.text = text;
    this.sent = System.currentTimeMillis();
  }
  
  public boolean expired() {
    return (System.currentTimeMillis() - sent) > FADE_TIME;
  }
  
}