package Graphics;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Math.Rotation;
import Math.Vector2D;

public class ShipControlSprite extends Graphic {

  public static final double CONTROL_RADIUS     = 2;
  public static final double BUTTON_RADIUS      = .2;
  
  private static final int CIRCLE_SAMPLES       = 40;
  private static final double CIRCLE_INCREMENT  = 2*Math.PI/CIRCLE_SAMPLES;

  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    drawCircle(gl, 0, 0, CONTROL_RADIUS, GL2.GL_LINE_LOOP);
    drawCircle(gl, 0, BUTTON_RADIUS - CONTROL_RADIUS , BUTTON_RADIUS, GL2.GL_POLYGON);
  }

  private void drawCircle(GL2 gl, double x, double y, double radius, int style) {
    gl.glColor4f(1, 1, 1, 1);
    gl.glBegin(style);
    
    for(int i=0; i < CIRCLE_SAMPLES ; i++){
      gl.glVertex3d(
          x + Math.cos(i*CIRCLE_INCREMENT) * radius, 
          y + Math.sin(i*CIRCLE_INCREMENT) * radius,
          Vector2D.Z
        );
    }
    
    gl.glEnd();
  }

}
