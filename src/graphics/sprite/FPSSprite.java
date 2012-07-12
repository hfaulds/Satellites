package graphics.sprite;

import graphics.Sprite;

import javax.media.opengl.GL2;

import math.Vector2D;

import com.jogamp.opengl.util.gl2.GLUT;

public class FPSSprite extends Sprite {

  private static final long SECOND = (long)10e9;
  
  private final GLUT glut = new GLUT();
  private long lastTime = 0;

  public FPSSprite() {
    super(new Vector2D(10, 625));
  }

  @Override
  public void render(GL2 gl) {
    long fps = SECOND / (System.nanoTime() - lastTime);
    
    gl.glWindowPos2d(position.x, position.y);
    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, String.valueOf(fps));
    lastTime = System.nanoTime();
  }

}
