package graphics.sprite;

import graphics.Sprite;

import java.util.Stack;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import math.Vector2D;

public class MsgSprite extends Sprite {

  private final GLUT glut = new GLUT();
  private final Stack<String> messagesWaiting = new Stack<String>();
  
  private final int fadeTime = 5000;
  
  private String currentMessage = "test";
  private long messageSent = System.currentTimeMillis();
  
  public MsgSprite() {
    super(new Vector2D(50, 10));
  }

  @Override
  public void render(GL2 gl) {
    
    if((System.currentTimeMillis() - messageSent) > fadeTime) {
      messageSent = System.currentTimeMillis();
      if(messagesWaiting.size() > 0) {
        currentMessage = messagesWaiting.pop();
      } else {
        currentMessage = "";
      }
    }
    
    gl.glWindowPos2d(position.x, position.y);
    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, currentMessage);
  }

}
