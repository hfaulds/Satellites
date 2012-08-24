package ingame.actors.ship;


import ingame.actors.ShipActor;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ShipDirection extends Actor {

  public static final double CONTROL_RADIUS     = ShipControl.CONTROL_RADIUS + .5;
  private static final int CIRCLE_SAMPLES       = ShipControl.CIRCLE_SAMPLES;
  private static final double CIRCLE_INCREMENT  = ShipControl.CIRCLE_INCREMENT;
  private static final float LINE_WIDTH			= 2.5f;
  private static final double LINE_LENGTH       = 0.1;

  private final int mid    =     - CIRCLE_SAMPLES / 4;
  private final int start  = mid - (int)(CIRCLE_SAMPLES * LINE_LENGTH);
  private final int end    = mid + (int)(CIRCLE_SAMPLES * LINE_LENGTH);
  
  private int listID;

  public ShipDirection(ShipActor parent) {
    super(parent, new Vector2D(), new Rotation(), 0, new Mesh());
  }
  
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
  public void render(GL2 gl, GLU glu) {
    gl.glTranslated(parent.position.x, parent.position.y, Vector2D.Z);
    gl.glRotated(parent.rotation.toDegrees(), parent.rotation.x, parent.rotation.y, parent.rotation.z);
    gl.glCallList(listID);
  }
}
