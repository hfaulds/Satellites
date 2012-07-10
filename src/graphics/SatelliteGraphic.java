package graphics;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import math.Rotation;
import math.Vector2D;



public class SatelliteGraphic implements Graphic {

  protected double radius;
  
  private float[] diffuseColour  = {(float) Math.random(), (float) Math.random(), (float) Math.random(), 1};
  private float[] specularColour  = {(float) Math.random(), (float) Math.random(), (float) Math.random(), 1};
  
  final int slices = 32;
  final int stacks = 32;
  
  private int listID;

  private boolean init = false;
  
  public SatelliteGraphic(double radius) {
    this.radius = radius;
  }
  
  @Override
  public void init(GL2 gl, GLU glu){
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diffuseColour, 0);
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specularColour, 0);
      gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 1.0f);
      
      GLUquadric earth = glu.gluNewQuadric();
      glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
      glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
      glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
      
      glu.gluSphere(earth, radius, slices, stacks);
      glu.gluDeleteQuadric(earth);
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
}
