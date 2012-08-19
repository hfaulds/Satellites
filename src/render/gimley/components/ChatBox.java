package render.gimley.components;


import geometry.Vector2D;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import render.Renderer2D;

import net.msg.ChatMsg;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

public class ChatBox extends GComponent {
  
  private static final int MAX_MSG_DISPLAYED = 6;
  private static final int MESSAGE_HEIGHT = 15;
  private static final int INPUT_WIDTH = 200;
  
  private static final int MAX_INPUT = 40;

  private static final Font font = new Font("Helvetica", Font.PLAIN, 12);
  private static final TextRenderer textRenderer = new TextRenderer(font);
  
  private final GLUT glut = new GLUT();
  private final List<ChatMsg> messages = new LinkedList<ChatMsg>();
  
  private boolean open = false;
  private String input = "";

  private final Vector2D messagesOffset = new Vector2D(2, MESSAGE_HEIGHT + 5);
  
  public ChatBox() {
    super(new Vector2D(15, 10));
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    if(open) {
      gl.glPushMatrix();
      
      gl.glColor4d(1.0, 1.0, 1.0, 1.0);
      gl.glLineWidth(0.9f);
      
      Renderer2D.drawRect(gl, position.x - 2 , position.y - 5, INPUT_WIDTH + 4, MESSAGE_HEIGHT + 5);
      Renderer2D.drawRect(gl, position.x - 2 , position.y + MESSAGE_HEIGHT, INPUT_WIDTH + 4, MESSAGE_HEIGHT * MAX_MSG_DISPLAYED);
      
      gl.glEnable(GL2.GL_LIGHTING);
      gl.glWindowPos2d(messagesOffset.x + position.x, position.y);
      
      String input = new String(this.input);
      
      glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, fitMessage(input));
      
      gl.glPopMatrix();
      
      for(int i=0; i < messages.size() && i < MAX_MSG_DISPLAYED; i++) {
        ChatMsg message = messages.get(i);
        renderMessage(gl, i, message);
      }
    } else {
      for(int i=0; i < messages.size(); i++) {
        ChatMsg message = messages.get(i);
        if(message.expired()) {
          break;
        }
        renderMessage(gl, i, message);
      }
    }
    
  }

  private void renderMessage(GL2 gl, int i, ChatMsg message) {
    gl.glWindowPos2d(messagesOffset.x + position.x, messagesOffset.y + position.y + MESSAGE_HEIGHT * i);
    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, message.username + ": " + message.text);
  }
  
  private String fitMessage(String message) {
    if(textRenderer.getBounds(message).getWidth() > INPUT_WIDTH)
      return fitMessage(message.substring(1));
    else
      return message;
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

  public void closeInput() {
    clearInput();
    open = false;
  }

  public void openInput() {
    open = true;
  }

  public boolean isOpen() {
    return open;
  }

  @Override
  public boolean testClick(Vector2D position) {
    if(open) {
      //TODO: Collide
    }
    return false;
  }
}
