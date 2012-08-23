package gimley.components;



import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import core.geometry.Vector2D;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.render.Renderer2D;

public class ChatBox extends GComponent implements KeyListener {
  
  private static final int MAX_MSG_DISPLAYED = 6;
  private static final int MESSAGE_HEIGHT = 15;
  private static final int INPUT_WIDTH = 200;
  private static final int MAX_INPUT = 40;
  
  private final Vector2D messagesOffset = new Vector2D(2, MESSAGE_HEIGHT + 5);
  private final List<ChatMsg> messages = new LinkedList<ChatMsg>();
  
  private String input = "";

  private NetworkConnection connection;
  private String username;

  public ChatBox(GComponent parent, Vector2D position, String username, NetworkConnection connection) {
    super(parent, position);
    this.width = INPUT_WIDTH + 4;
    this.height = MESSAGE_HEIGHT * (MAX_MSG_DISPLAYED + 1) + 5;
    this.username = username;
    this.connection = connection;
    subcomponents.add(new TopBar(this, "Chat"));
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    super.render(gl, width, height);

    gl.glColor4d(0.6, 0.6, 0.6, 1.0);
    Renderer2D.drawFillRect(gl, position.x , position.y, 
        INPUT_WIDTH + 4, MESSAGE_HEIGHT + 5);
    
    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x , position.y, 
        INPUT_WIDTH + 4, MESSAGE_HEIGHT + 5, 0.9f);

    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y + MESSAGE_HEIGHT + 5, 
        INPUT_WIDTH + 4, MESSAGE_HEIGHT * MAX_MSG_DISPLAYED);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x, position.y + MESSAGE_HEIGHT + 5, 
        INPUT_WIDTH + 4, MESSAGE_HEIGHT * MAX_MSG_DISPLAYED, 0.9f);
    
    Renderer2D.drawText(gl, messagesOffset.x + position.x, position.y + 5, Renderer2D.fitString(gl, input, INPUT_WIDTH));
    
    for(int i=0; i < messages.size() && i < MAX_MSG_DISPLAYED; i++) {
      ChatMsg message = messages.get(i);
      renderMessage(gl, i, message);
    }
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

  public String getInput() {
    return input;
  }

  public void addChar(char c) {
    if(input.length() <= MAX_INPUT)
      input = input + c;
  }

  public void backSpace() {
    int length = input.length();
    if(length > 0)
      input = input.substring(0, length - 1);
  }

  public void clearInput() {
    input = "";
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    switch(keyCode) {
      case 10: // enter
        if(getInput().length() > 0) {
          ChatMsg msg = new ChatMsg(getInput(), username);
          displayMessage(msg);
          connection.sendMsg(msg);
          System.out.println("MESSAGE SENT");
          clearInput();
        }
        break;
      case 8: // backspace
        backSpace();
        break;
      default:
        char character = (char)keyCode;
        if(!e.isShiftDown())
          character =  Character.toLowerCase(character);
        addChar(character);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }
  
}
