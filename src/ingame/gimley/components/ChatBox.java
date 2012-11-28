package ingame.gimley.components;

import ingame.gimley.listeners.ChatMsgListener;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.KeyEvent;

import core.geometry.Vector2D;
import core.gimley.actions.ActionEvent;
import core.gimley.actions.InputEntered;
import core.gimley.components.GComponent;
import core.gimley.components.GPanel;
import core.gimley.components.GTextInput;
import core.gimley.listeners.ActionListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.render.Renderer2D;

public class ChatBox extends GPanel {
  
  private static final int MAX_MSGS       = 6;
  private static final int MESSAGE_HEIGHT = 15;
  
  private static final int WIDTH          = 200;
  private static final int HEIGHT = MESSAGE_HEIGHT * (MAX_MSGS + 1) + 5;
  
  private final Vector2D messageOffset = new Vector2D(2, MESSAGE_HEIGHT + 5);
  private final List<ChatMsg> messages = new LinkedList<ChatMsg>();

  private final GTextInput input = new GTextInput(this, new Vector2D(), WIDTH, MESSAGE_HEIGHT + 5);

  public ChatBox(GComponent parent, Vector2D position, final NetworkConnection connection) {
    super(parent, "ChatBox", position, WIDTH, HEIGHT);
    
    input.addActionListener(new ActionListener(){
      @Override
      public void action(ActionEvent action) {
        if(action instanceof InputEntered) {
          InputEntered inputAction = (InputEntered) action;
          ChatMsg msg = new ChatMsg(inputAction.input, connection.username);
          displayMessage(msg);
          connection.sendMsg(msg);
        }
      }
    });
    
    add(input);
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    super.render(gl, width, height);
    renderMessages(gl);
  }

  private void renderMessages(GL2 gl) {
    for(int i=0; i < messages.size() && i < MAX_MSGS; i++) {
      ChatMsg message = messages.get(i);
      
      Renderer2D.drawText(gl, 
        messageOffset.x + position.x + 2, 
        messageOffset.y + position.y + MESSAGE_HEIGHT * i + 5, 
        message.username + ": " + message.text
        );
    }
  }
  
  public void displayMessage(ChatMsg msg) {
    messages.add(0, msg);
  }
  
  /* Key Handling */
  
  public void keyPressed(KeyEvent e) {
    input.keyPressed(e);
  }
  
  public void keyReleased(KeyEvent e) {
    input.keyReleased(e);
  }

  public static ChatBox createChatBox(GComponent parent, NetworkConnection connection) {
    ChatBox chatBox = new ChatBox(parent, new Vector2D(15, 15), connection);
    connection.addMsgListener(new ChatMsgListener(chatBox));
    return chatBox;
  }
}
