package scene.graphics;

import java.io.FileNotFoundException;

import geometry.Box;
import geometry.Mesh;
import geometry.MeshLoader;
import geometry.Rotation;
import geometry.Vector2D;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class Planet1000Graphic implements Graphic {

  private static final String PLANET_MESH = "Planet 1000.obj";
  
  private final float[] ambientColour = {0.2f, 0.3f, 0.5f};
  
  private final Mesh mesh;
  public final Box boundingbox;

  private int listID;

  public Planet1000Graphic() {
    this.mesh = loadMesh(PLANET_MESH);
    this.boundingbox = Box.createBoundingBox(mesh);
  }

  private Mesh loadMesh(String file) {
    try {
      return MeshLoader.loadOBJ(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  @Override
  public void init(GL2 gl, GLU glu) {
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,  ambientColour , 0);
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, ambientColour, 0);
      gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
    
      mesh.render(gl);
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
