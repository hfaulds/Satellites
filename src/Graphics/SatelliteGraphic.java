package Graphics;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import Math.Rotation;
import Math.Vector2D;


public class SatelliteGraphic extends Graphic {

  protected double radius;
  
  private final float[] ambientColour  = {(float) Math.random(), (float) Math.random(), (float) Math.random()};
  private final float[] specularColour = {(float) Math.random(), (float) Math.random(), (float) Math.random()};//{0.8f, 0.8f, 0.8f, 1.0f};
  private final float[] lightPos       = {0, 0, 20, 1};
  
  final int slices = 32;
  final int stacks = 32;
  
  public SatelliteGraphic(double radius) {
    this.radius = radius;
  }
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    
    gl.glPushMatrix();
    {
      gl.glTranslated(pos.x, pos.y, Vector2D.Z);
      gl.glColor3fv(ambientColour, 1);
      
      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT,  ambientColour, 0);
      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, specularColour, 0);

      gl.glEnable(GL2.GL_LIGHT1);
      gl.glEnable(GL2.GL_LIGHTING);

      float[] rgba = {1f, 1f, 1f};
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
      gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
      
      GLUquadric earth = glu.gluNewQuadric();
      glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
      glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
      glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
      
      glu.gluSphere(earth, radius, slices, stacks);
      glu.gluDeleteQuadric(earth);
    }
    gl.glPopMatrix();
  }
}
