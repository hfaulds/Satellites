package graphics.ship;

import graphics.Graphic;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import math.Rotation;
import math.Vector2D;


public class ShipDirectionSprite extends Graphic {

  public static final double CONTROL_RADIUS     = ShipControlSprite.CONTROL_RADIUS + ShipControlSprite.BUTTON_RADIUS;
  private static final int CIRCLE_SAMPLES       = ShipControlSprite.CIRCLE_SAMPLES;
  private static final double CIRCLE_INCREMENT  = ShipControlSprite.CIRCLE_INCREMENT;
  
  private static final float LINE_WIDTH			    = ShipControlSprite.LINE_WIDTH;
  private static final double LINE_LENGTH       = .1;

  private final int length = (int)(1 / LINE_LENGTH);
  private final int mid    =     - CIRCLE_SAMPLES / 4;
  private final int start  = mid - CIRCLE_SAMPLES / length;
  private final int end    = mid + CIRCLE_SAMPLES / length;
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    gl.glPushMatrix();
    {
      gl.glDisable(GL2.GL_LIGHT1);
      gl.glDisable(GL2.GL_LIGHTING);
      gl.glColor4d(1.0, 1.0, 1.0, 1.0);
      gl.glLineWidth(LINE_WIDTH);
      gl.glBegin(GL2.GL_LINE_STRIP);
      
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
