package graphics.sprite;

import graphics.Sprite;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import math.Vector2D;
import net.msg.ChatMsg;

import com.jogamp.opengl.util.gl2.GLUT;

public class MsgSprite extends Sprite {
  
  private static final int LINE_PX = 15;

  private final GLUT glut = new GLUT();
  private final List<ChatMsg> messages = new LinkedList<ChatMsg>();
  
  public boolean inputting = false;
  private String input = "";
  
  public MsgSprite() {
    super(new Vector2D(15, 10));
    try {
      messages.add(new ChatMsg("Hello"));
      Thread.sleep(1000);
      messages.add(new ChatMsg("Hi"));
      Thread.sleep(1000);
      messages.add(new ChatMsg("Screw You!"));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void render(GL2 gl) {
    if(inputting) {
      gl.glDisable(GL2.GL_LIGHTING);
      
      gl.glColor4d(1.0, 1.0, 1.0, 1.0);
      gl.glLineWidth(0.9f);
      
      gl.glBegin(GL2.GL_LINE_LOOP);
      gl.glVertex2d(position.x - 2,     position.y + 615 - 1);
      gl.glVertex2d(position.x - 2,     position.y + LINE_PX + 615 + 1);
      gl.glVertex2d(position.x + 200,   position.y + LINE_PX + 615 + 1);
      gl.glVertex2d(position.x + 200,   position.y + 615 - 1);
      gl.glEnd();
      
      gl.glEnable(GL2.GL_LIGHTING);
      gl.glWindowPos2d(position.x, position.y);
      glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, input);
    }
    
    int i = 0;
    while(i < messages.size() && messages.size() != 0)
    {
      ChatMsg message = messages.get((messages.size() - i - 1));
      if(!message.expired()) {
        gl.glWindowPos2d(position.x, position.y + ((i+1) * LINE_PX + 1));
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, message.owner + " :" + message.text);
      } else {
        messages.remove(message);
      }
      i++;
    }
  }

  public void addMessage(ChatMsg msg) {
    messages.add(msg);
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }
}
