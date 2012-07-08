package Graphics.Ship;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Graphics.Graphic;
import Math.Rotation;
import Math.Vector2D;

public class ShipControlSprite extends Graphic {

  public static final double CONTROL_RADIUS     = 2;
  public static final double BUTTON_RADIUS      = .25;
  public static final int CIRCLE_SAMPLES        = 40;
  public static final double CIRCLE_INCREMENT   = 2*Math.PI/CIRCLE_SAMPLES;

  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    drawCircle(gl, 0, 0, CONTROL_RADIUS, GL2.GL_LINE_LOOP, 1);
    
    for(int i=1; i < 4; i++) {
      double x = Math.cos((1+1.5*i)*CIRCLE_INCREMENT) * CONTROL_RADIUS;
      double y = Math.sin((1+1.5*i)*CIRCLE_INCREMENT) * CONTROL_RADIUS;
  
      drawCircle(gl, x, y, BUTTON_RADIUS, GL2.GL_LINE_LOOP);
      drawCircle(gl, x, y, BUTTON_RADIUS-0.01, GL2.GL_POLYGON, 0.2);
    }
    
  }

  private void drawCircle(GL2 gl, double x, double y, double radius, int style) {
    this.drawCircle(gl, x, y, radius, style, 1.0);
  }
  
  private void drawCircle(GL2 gl, double x, double y, double radius, int style, double shade) {
    gl.glPushMatrix();
    {
      gl.glDisable(GL2.GL_LIGHT1);
      gl.glDisable(GL2.GL_LIGHTING);
      gl.glColor4d(shade, shade, shade, 1.0);
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
    gl.glPopMatrix();
  }

}
