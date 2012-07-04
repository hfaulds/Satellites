package Graphics;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class ShipGraphic extends Graphic {

  private final float[] ambientColour   = { 0.4f, 0.4f, 0.4f };
  private final float[] specularColour  = { 0.8f, 0.8f, 0.8f, 1.0f };

  private final float[] lightPos        = { -30,  0.0f,  20f, 1.0f };
  
  private final double width;
  private final double height;
  private final double depth;

  public ShipGraphic(double width, double height, double depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
  }

  @Override
  public void render(GL2 gl, GLU glu, double x, double y) {

    gl.glPushMatrix();
    {
      gl.glLoadIdentity();
      gl.glTranslated(x, y, -10);

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
        gl.glVertex3d( width,  height,  depth);
        gl.glVertex3d(-width,  height,  depth);
        gl.glVertex3d(-width, -height,  depth);
        gl.glVertex3d( width, -height,  depth);
        
        // Back-face
        gl.glVertex3d( width, -height, -depth);
        gl.glVertex3d(-width, -height, -depth);
        gl.glVertex3d(-width,  height, -depth);
        gl.glVertex3d( width,  height, -depth);
        
        // Left-face
        gl.glVertex3d(-width,  height,  depth);
        gl.glVertex3d(-width,  height, -depth);
        gl.glVertex3d(-width, -height, -depth);
        gl.glVertex3d(-width, -height,  depth);
      
        // Right-face
        gl.glVertex3d( width,  height, -depth);
        gl.glVertex3d( width,  height,  depth);
        gl.glVertex3d( width, -height,  depth);
        gl.glVertex3d( width, -height, -depth);
        
        // Top-face
        gl.glVertex3d( width,  height, -depth);
        gl.glVertex3d(-width,  height, -depth);
        gl.glVertex3d(-width,  height,  depth);
        gl.glVertex3d( width,  height,  depth);
        
        // Bottom-face
        gl.glVertex3d( width, -height,  depth);
        gl.glVertex3d(-width, -height,  depth);
        gl.glVertex3d(-width, -height, -depth);
        gl.glVertex3d( width, -height, -depth);
      }
      gl.glEnd();
      
      gl.glPopMatrix();
    }
  }
}
