package ingame.gimley.components;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;

public class FPSCounter extends GComponent {

  private static final long SECOND = (long)10e8;
  
  private final GLUT glut = new GLUT();
  private long lastTime = System.nanoTime();
  private long fps = 0;

  public FPSCounter(GComponent parent, Vector2D position) {
    super(parent, position);
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    fps = (fps * 5/6) +  (SECOND / (System.nanoTime() - lastTime) * 1/6);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    gl.glWindowPos2d(position.x, height + position.y);
    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, String.valueOf(fps));
    lastTime = System.nanoTime();
  }

  @Override
  public boolean testClick(Vector2D position) {
    return false;
  }

}
