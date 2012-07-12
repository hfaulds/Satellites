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
  
  public MsgSprite() {
    super(new Vector2D(10, 10));
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
    int i = 0;
    while(i < messages.size() && messages.size() != 0)
    {
      ChatMsg message = messages.get((messages.size() - i - 1));
      if(!message.expired()) {
        gl.glWindowPos2d(position.x, position.y + (i * LINE_PX));
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, message.text);
      } else {
        messages.remove(message);
      }
      i++;
    }
  }

  public void addMessage(ChatMsg msg) {
    messages.add(msg);
  }
}
