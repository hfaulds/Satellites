package graphics;

import javax.media.opengl.GL2;

import math.Vector2D;

import com.jogamp.opengl.util.gl2.GLUT;

public class FPSSprite extends Sprite {

  private final GLUT glut = new GLUT();
  
  private long lastTime = 0;

  public FPSSprite() {
    super(new Vector2D(0,0));
  }

  @Override
  public void render(GL2 gl) {
    long second = (long)10e9;
    long dif = System.nanoTime() - lastTime;
    long fps = second/dif ;
    gl.glWindowPos2d(10, 20);
    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, String.valueOf(fps));
    lastTime = System.nanoTime();
  }

}
