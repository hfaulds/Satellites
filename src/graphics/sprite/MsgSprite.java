package graphics.sprite;

import graphics.Sprite;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import math.Vector2D;

import com.jogamp.opengl.util.gl2.GLUT;

public class MsgSprite extends Sprite {
  
  private static final int LINE_PX = 15;

  private static final int FADE_TIME = 10000;

  private final GLUT glut = new GLUT();
  private final List<Message> messages = new LinkedList<Message>();
  
  private class Message {
    
    public final String text;
    private long sent;
    
    public Message(String text) {
      this.text = text;
      this.sent = System.currentTimeMillis();
    }
    
    public boolean expired() {
      return (System.currentTimeMillis() - sent) > FADE_TIME;
    }
    
  };
  
  public MsgSprite() {
    super(new Vector2D(10, 10));
    try {
      messages.add(new Message("Hello"));
      Thread.sleep(1000);
      messages.add(new Message("Hi"));
      Thread.sleep(1000);
      messages.add(new Message("Screw You!"));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void render(GL2 gl) {
    int i = 0;
    while(i < messages.size() && messages.size() != 0)
    {
      Message message = messages.get((messages.size() - i - 1));
      if(!message.expired()) {
        gl.glWindowPos2d(position.x, position.y + (i * LINE_PX));
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, message.text);
      } else {
        messages.remove(message);
      }
      i++;
    }
  }

  public void addMessage(String text) {
    messages.add(new Message(text));
  }
}
