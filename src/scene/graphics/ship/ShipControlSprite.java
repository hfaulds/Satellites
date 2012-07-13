package scene.graphics.ship;


import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import scene.graphics.Graphic;

import math.Rotation;
import math.Vector2D;


public class ShipControlSprite implements Graphic {

  public static final double CONTROL_RADIUS     = 2;
  public static final double BUTTON_RADIUS      = .25;
  
  public static final int CIRCLE_SAMPLES        = 40;
  public static final double CIRCLE_INCREMENT   = 2*Math.PI/CIRCLE_SAMPLES;
  
  public static final float LINE_WIDTH			    = 2f;

  private int listID;
  private boolean init = false;
  
  @Override
  public void init(GL2 gl, GLU glu) {
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      gl.glPushMatrix();
      {
        gl.glDisable(GL2.GL_LIGHTING);
        
        drawCircle(gl, 0, 0, CONTROL_RADIUS, GL2.GL_LINE_LOOP, 1);
          
        for(int i=1; i < 4; i++) {
          double x = Math.cos( (1+1.5*i) * CIRCLE_INCREMENT) * CONTROL_RADIUS;
          double y = Math.sin( (1+1.5*i) * CIRCLE_INCREMENT) * CONTROL_RADIUS;
        
          drawCircle(gl, x, y, BUTTON_RADIUS, GL2.GL_LINE_LOOP);
          drawCircle(gl, x, y, BUTTON_RADIUS - 0.01, GL2.GL_POLYGON, 0.2);
        }

        gl.glEnable(GL2.GL_LIGHTING);
      }
      gl.glPopMatrix();
    }
    gl.glEndList();
  }
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    if(!init )
      init(gl, glu);

    gl.glPushMatrix();
    {
      gl.glTranslated(pos.x, pos.y, Vector2D.Z);
      gl.glCallList(listID);
    }
    gl.glPopMatrix();
  }

  private void drawCircle(GL2 gl, double x, double y, double radius, int style) {
    this.drawCircle(gl, x, y, radius, style, 1.0);
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
