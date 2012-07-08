package Graphics.Ship;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Graphics.Graphic;
import Math.Rotation;
import Math.Vector2D;

public class ShipGraphic extends Graphic {

  private final float[] ambientColour   = { 0.4f, 0.4f, 0.4f };
  private final float[] specularColour  = { 0.8f, 0.8f, 0.8f, 1.0f };

  private final float[] lightPos        = { -30,  0.0f,  20f, 1.0f };
  
  private final double width;
  private final double length;
  private final double height;
  
  private Graphic controlsUI = new ShipControlSprite();
  private Graphic directionUI = new ShipDirectionSprite();

  public ShipGraphic(double width, double length, double height) {
    this.width  = width;
    this.length = length;
    this.height  = height;
  }

  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    gl.glPushMatrix();
    {
      gl.glLoadIdentity();
      gl.glTranslated(pos.x, pos.y, Vector2D.Z);

      gl.glPushMatrix();
      {
        gl.glRotated(rot.degrees(), rot.x, rot.y, rot.z);
        
        directionUI.render(gl, glu, pos, rot);
        
        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos,       0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT,  ambientColour,  0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, specularColour, 0);
  
        // Enable lighting in GL.
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);
  
        // Set material properties.
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
      gl.glPopMatrix();
      
      
      controlsUI.render(gl, glu, pos, rot);
    }
    gl.glPopMatrix();
  }
}
