package ingame.gimley.components;


import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.gimley.ActionListener;
import core.gimley.components.GComponent;
import core.gimley.components.GTextInput;
import core.gimley.components.GTopBar;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.render.Renderer2D;

public class ChatBox extends GComponent {
  
  private static final int MAX_MSGS       = 6;
  private static final int MESSAGE_HEIGHT = 15;
  private static final int WIDTH          = 200;
  
  private final Vector2D messageOffset = new Vector2D(2, MESSAGE_HEIGHT + 5);
  private final List<ChatMsg> messages = new LinkedList<ChatMsg>();

  private final GTextInput input = new GTextInput(this, new Vector2D(), WIDTH, MESSAGE_HEIGHT + 5);

  public ChatBox(GComponent parent, Vector2D position, final NetworkConnection connection) {
    super(parent, position);
    
    this.width = WIDTH;
    this.height = MESSAGE_HEIGHT * (MAX_MSGS + 1) + 5;
    
    input.addActionListener(new ActionListener(){
      @Override
      public void action() {
        ChatMsg msg = new ChatMsg(input.getInput(), connection.getUsername());
        displayMessage(msg);
        connection.sendMsg(msg);
        input.clearInput();
      }
    });

    add(new GTopBar(this, "Chat", true, true));
    add(input);
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y, 
        this.width, this.height);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x, position.y, 
        this.width, this.height, 0.9f);
    
    for(int i=0; i < messages.size() && i < MAX_MSGS; i++) {
      ChatMsg message = messages.get(i);
      renderMessage(gl, i, message);
    }
    
    super.render(gl, width, height);
  }

  private void renderMessage(GL2 gl, int i, ChatMsg message) {
    Renderer2D.drawText(gl, 
        messageOffset.x + position.x + 2, 
        messageOffset.y + position.y + MESSAGE_HEIGHT * i + 5, 
        message.username + ": " + message.text
        );
  }
  
  public void displayMessage(ChatMsg msg) {
    messages.add(0, msg);
  }
}
