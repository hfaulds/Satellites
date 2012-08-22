package scene.controllers.ui;


import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.geometry.Rotation;
import core.geometry.Vector2D;

import scene.controllers.PlayerInputController;

public class ShipAimGraphic implements Graphic {

  public static final double CONTROL_RADIUS     = ShipControlGraphic.CONTROL_RADIUS + .65;
  private static final int CIRCLE_SAMPLES       = 300;
  private static final double CIRCLE_INCREMENT  = 2*Math.PI/CIRCLE_SAMPLES;
  private static final float LINE_WIDTH         = 2.5f;
  private static final double LINE_LENGTH       = 0.075;

  private final int mid    =     - CIRCLE_SAMPLES / 4;
 
  
  private PlayerInputController controller;

  public ShipAimGraphic(PlayerInputController controller) {
    this.controller = controller;
  }
  
  @Override
  public void init(GL2 gl, GLU glu) {
    
  }
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    
    gl.glTranslated(pos.x, pos.y, Vector2D.Z);
    gl.glRotated(Math.toDegrees(Rotation.XRotFromvector(controller.aimDirection).mag), rot.x, rot.y, rot.z);
    
    double l = PlayerInputController.GUN_COOLDOWN - controller.timeTillNextFire;
    double length = l / PlayerInputController.GUN_COOLDOWN;
    
    int start  = mid - (int)(CIRCLE_SAMPLES * LINE_LENGTH * length);
    int end    = mid + (int)(CIRCLE_SAMPLES * LINE_LENGTH * length);
    
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
  
}
