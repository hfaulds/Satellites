package Graphics.Ship;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Graphics.Graphic;
import Math.Rotation;
import Math.Vector2D;

public class ShipDirectionSprite extends Graphic {

  public static final double CONTROL_RADIUS     = ShipControlSprite.CONTROL_RADIUS + ShipControlSprite.BUTTON_RADIUS;
  private static final int CIRCLE_SAMPLES       = ShipControlSprite.CIRCLE_SAMPLES;
  private static final double CIRCLE_INCREMENT  = ShipControlSprite.CIRCLE_INCREMENT;

  public static final double DIRECTION_LENGTH      = .25;
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    gl.glPushMatrix();
    {
      gl.glDisable(GL2.GL_LIGHT1);
      gl.glDisable(GL2.GL_LIGHTING);
      gl.glColor4d(1.0, 1.0, 1.0, 1.0);
      gl.glBegin(GL2.GL_LINE_STRIP);
      
      int mid =  - CIRCLE_SAMPLES/4;
      int start = mid - CIRCLE_SAMPLES/8;
      int end = mid + CIRCLE_SAMPLES/8;
      
      for(int i=start; i < end ; i++){
        gl.glVertex3d(
            Math.cos(i * CIRCLE_INCREMENT) * CONTROL_RADIUS, 
            Math.sin(i * CIRCLE_INCREMENT) * CONTROL_RADIUS,
            Vector2D.Z
          );
      }
      
      gl.glEnd();
    }
    gl.glPopMatrix();
  }
}
