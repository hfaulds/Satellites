package scene.graphics.ship;


import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import scene.graphics.Graphic;

import math.Rotation;
import math.Vector2D;

public class ShipDirectionGraphic implements Graphic {

  public static final double CONTROL_RADIUS     = ShipControlGraphic.CONTROL_RADIUS + .5;
  private static final int CIRCLE_SAMPLES       = ShipControlGraphic.CIRCLE_SAMPLES;
  private static final double CIRCLE_INCREMENT  = ShipControlGraphic.CIRCLE_INCREMENT;
  private static final float LINE_WIDTH			= 2.5f;
  private static final double LINE_LENGTH       = .1;

  private final int mid    =     - CIRCLE_SAMPLES / 4;
  private final int start  = mid - (int)(CIRCLE_SAMPLES * LINE_LENGTH);
  private final int end    = mid + (int)(CIRCLE_SAMPLES * LINE_LENGTH);
  
  private int listID;

  @Override
  public void init(GL2 gl, GLU glu) {
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      gl.glPushMatrix();
      {
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

        gl.glEnable(GL2.GL_LIGHTING);
      }
      gl.glPopMatrix();
    }
    gl.glEndList();
  }
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    gl.glTranslated(pos.x, pos.y, Vector2D.Z);
    gl.glRotated(rot.toDegrees(), rot.x, rot.y, rot.z);
    gl.glCallList(listID);
  }
}
