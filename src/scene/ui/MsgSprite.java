package scene.ui;


import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import math.Vector2D;
import net.msg.ChatMsg;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

public class MsgSprite extends Sprite {
  

  private static final int MESSAGE_HEIGHT = 15;
  private static final int INPUT_WIDTH = 200;
  
  private static final int MAX_INPUT = 40;

  private static final Font font = new Font("Helvetica", Font.PLAIN, 12);
  private static final TextRenderer textRenderer = new TextRenderer(font);
  
  private final GLUT glut = new GLUT();
  private final List<ChatMsg> messages = new LinkedList<ChatMsg>();
  
  public boolean inputting = false;
  private String input = "";

  private final Vector2D messagesPos = position.add(new Vector2D(2, MESSAGE_HEIGHT + 5));
  
  public MsgSprite() {
    super(new Vector2D(15, 10));
  }

  @Override
  public void render(GL2 gl) {
    if(inputting) {
      gl.glPushMatrix();
      
      gl.glColor4d(1.0, 1.0, 1.0, 1.0);
      gl.glLineWidth(0.9f);

      gl.glBegin(GL2.GL_LINE_LOOP);
      gl.glVertex2d(position.x - 2              , position.y - 5);
      gl.glVertex2d(position.x - 2              , position.x + MESSAGE_HEIGHT /2);
      gl.glVertex2d(position.x + INPUT_WIDTH + 2, position.x + MESSAGE_HEIGHT / 2);
      gl.glVertex2d(position.x + INPUT_WIDTH + 2, position.y - 5);
      gl.glEnd();
      
      gl.glEnable(GL2.GL_LIGHTING);
      gl.glWindowPos2d(messagesPos.x, position.y);
      
      
      String input = new String(this.input);
      
      glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, fitMessage(input));
      
      gl.glPopMatrix();
    }
    
    int i = 0;
    while(i < messages.size() && messages.size() != 0)
    {
      ChatMsg message = messages.get((messages.size() - i - 1));
      if(!message.expired()) {
        gl.glWindowPos2d(messagesPos.x, messagesPos.y + MESSAGE_HEIGHT * i);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, message.owner + ": " + message.text);
      } else {
        messages.remove(message);
      }
      i++;
    }
  }
  
  private String fitMessage(String message) {
    if(textRenderer.getBounds(message).getWidth() > INPUT_WIDTH)
      return fitMessage(message.substring(1));
    else
      return message;
  }

  public void displayMessage(ChatMsg msg) {
    messages.add(msg);
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
}
