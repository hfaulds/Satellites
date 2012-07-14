package scene.graphics.ship;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import math.Rotation;
import math.Vector2D;
import scene.graphics.Graphic;

public class ShipControlGraphic implements Graphic {

  public static final double CONTROL_RADIUS     = 2;
  public static final double BUTTON_RADIUS      = .25;
  
  public static final int CIRCLE_SAMPLES        = 40;
  public static final double CIRCLE_INCREMENT   = 2*Math.PI/CIRCLE_SAMPLES;
  
  public static final float LINE_WIDTH			= 3.5f;

  private int listID;
  
  @Override
  public void init(GL2 gl, GLU glu) {
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      gl.glPushMatrix();
      {
        gl.glDisable(GL2.GL_LIGHTING);
        
        drawCircle(gl, 0, 0, CONTROL_RADIUS, GL2.GL_LINE_LOOP, 1);
      }
      gl.glPopMatrix();
    }
    gl.glEndList();
  }
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    gl.glTranslated(pos.x, pos.y, Vector2D.Z);
    gl.glCallList(listID);
  }
  
  private void drawCircle(GL2 gl, double x, double y, double radius, int style, double shade) {
      gl.glColor4d(shade, shade, shade, 1.0);

      gl.glLineWidth(LINE_WIDTH);
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