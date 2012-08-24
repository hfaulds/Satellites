package gimley.components;

import gimley.core.ActionListener;
import gimley.core.components.GComponent;
import gimley.core.components.GTextInput;
import gimley.core.components.GTopBar;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.render.Renderer2D;

public class ChatBox extends GComponent {
  
  private static final int MAX_MSG_DISPLAYED = 6;
  private static final int MESSAGE_HEIGHT = 15;
  private static final int INPUT_WIDTH = 200;
  
  private final Vector2D messagesOffset = new Vector2D(2, MESSAGE_HEIGHT + 5);
  private final List<ChatMsg> messages = new LinkedList<ChatMsg>();

  private final GTextInput input = new GTextInput(this, new Vector2D(), INPUT_WIDTH + 4, MESSAGE_HEIGHT + 5);

  public ChatBox(GComponent parent, Vector2D position, final NetworkConnection connection) {
    super(parent, position);
    
    this.width = INPUT_WIDTH + 4;
    this.height = MESSAGE_HEIGHT * (MAX_MSG_DISPLAYED + 1) + 5;
    
    input.addActionListener(new ActionListener(){
      @Override
      public void action() {
        ChatMsg msg = new ChatMsg(input.getInput(), connection.getUsername());
        displayMessage(msg);
        connection.sendMsg(msg);
        input.clearInput();
      }
    });

    GTopBar topBar = new GTopBar(this, "Chat", true, true);
    subcomponents.add(topBar);
    subcomponents.add(input);
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y, 
        INPUT_WIDTH + 4, MESSAGE_HEIGHT * (MAX_MSG_DISPLAYED + 1) + 5);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x, position.y, 
        INPUT_WIDTH + 4, MESSAGE_HEIGHT * (MAX_MSG_DISPLAYED + 1) + 5, 0.9f);
    
    for(int i=0; i < messages.size() && i < MAX_MSG_DISPLAYED; i++) {
      ChatMsg message = messages.get(i);
      renderMessage(gl, i, message);
    }
    
    super.render(gl, width, height);
  }

  private void renderMessage(GL2 gl, int i, ChatMsg message) {
    Renderer2D.drawText(gl, 
        messagesOffset.x + position.x + 2, 
        messagesOffset.y + position.y + MESSAGE_HEIGHT * i + 5, 
        message.username + ": " + message.text
        );
  }
  
  public void displayMessage(ChatMsg msg) {
    messages.add(0, msg);
  }
}
