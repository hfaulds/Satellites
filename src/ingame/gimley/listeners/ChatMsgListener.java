package ingame.gimley.listeners;

import ingame.gimley.components.ChatBox;

import com.esotericsoftware.kryonet.Connection;

import core.net.msg.MsgListener;
import core.net.msg.ingame.ChatMsg;

public class ChatMsgListener implements MsgListener {
  
  private final ChatBox chatBox;

  public ChatMsgListener(ChatBox chatBox) {
    this.chatBox = chatBox;
  }

  @Override
  public void msgReceived(Object msg, Connection reply) {
    chatBox.displayMessage((ChatMsg) msg);
  }

  @Override
  public Class<?> getMsgClass() {
    return ChatMsg.class;
  }
}