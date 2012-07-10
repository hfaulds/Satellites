package graphics.ship;

import graphics.Graphic;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import math.Rotation;
import math.Vector2D;


public class ShipGraphic implements Graphic {

  private final float[] ambientColour   = { 0.7f, 0.7f, 0.7f };
  
  private final double width;
  private final double length;
  private final double height;
  
  private Graphic controlsUI = new ShipControlSprite();
  private Graphic directionUI = new ShipDirectionSprite();

  private boolean init = false;
  private int listID;

  public ShipGraphic(double width, double length, double height) {
    this.width  = width;
    this.length = length;
    this.height  = height;
  }

  @Override
  public void init(GL2 gl, GLU glu) {
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,  ambientColour, 0);
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, ambientColour, 0);
      gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
    
      gl.glBegin(GL2.GL_QUADS);
      {
        // Front-face
        gl.glVertex3d( width,  length,  height);
        gl.glVertex3d(-width,  length,  height);
        gl.glVertex3d(-width, -length,  height);
        gl.glVertex3d( width, -length,  height);
        
        // Back-face
        gl.glVertex3d( width, -length, -height);
        gl.glVertex3d(-width, -length, -height);
        gl.glVertex3d(-width,  length, -height);
        gl.glVertex3d( width,  length, -height);
        
        // Left-face
        gl.glVertex3d(-width,  length,  height);
        gl.glVertex3d(-width,  length, -height);
        gl.glVertex3d(-width, -length, -height);
        gl.glVertex3d(-width, -length,  height);
      
        // Right-face
        gl.glVertex3d( width,  length, -height);
        gl.glVertex3d( width,  length,  height);
        gl.glVertex3d( width, -length,  height);
        gl.glVertex3d( width, -length, -height);
        
        // Top-face
        gl.glVertex3d( width,  length, -height);
        gl.glVertex3d(-width,  length, -height);
        gl.glVertex3d(-width,  length,  height);
        gl.glVertex3d( width,  length,  height);
        
        // Bottom-face
        gl.glVertex3d( width, -length,  height);
        gl.glVertex3d(-width, -length,  height);
        gl.glVertex3d(-width, -length, -height);
        gl.glVertex3d( width, -length, -height);
      }
      gl.glEnd();
    }
    gl.glEndList();
  }
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    if(!init)
      init(gl, glu);
    
    gl.glPushMatrix();
    {
      gl.glLoadIdentity();
      gl.glTranslated(pos.x, pos.y, Vector2D.Z);
      controlsUI.render(gl, glu, pos, rot);

      gl.glPushMatrix();
      {
        gl.glRotated(rot.toDegrees(), rot.x, rot.y, rot.z);
        
        directionUI.render(gl, glu, pos, rot);
        gl.glCallList(listID);
      }
      gl.glPopMatrix();
      
    }
    gl.glPopMatrix();
  }

}
