package graphics.sprite;

import graphics.Sprite;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import math.Vector2D;

import com.jogamp.opengl.util.gl2.GLUT;

public class MsgSprite extends Sprite {
  
  private static final int FADE_TIME = 5000;

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
    super(new Vector2D(50, 10));
  }

  @Override
  public void render(GL2 gl) {
    
    for(int i=0; i < messages.size(); i++) {
      Message message = messages.get(i);
      if(!message.expired()) {
        gl.glWindowPos2d(position.x, position.y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, message.text);
      } else {
        //messages.remove(message);
      }
    }
  }

  public void addMessage(String text) {
    messages.add(new Message(text));
  }
}
