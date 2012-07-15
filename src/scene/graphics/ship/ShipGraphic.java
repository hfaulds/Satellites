package scene.graphics.ship;

import geometry.Box;
import geometry.Mesh;
import geometry.MeshLoader;
import geometry.Rotation;
import geometry.Vector2D;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import scene.graphics.Graphic;


public class ShipGraphic implements Graphic {

  private static final String SHIP_MESH = "Ship-Mk2.obj";
  private final float[] ambientColour   = { 0.7f, 0.7f, 0.7f };

  private int listID;
  
  public final List<Graphic> ui = new ArrayList<Graphic>();

  public final Box boundingbox;
  public final Mesh mesh;

  public ShipGraphic(double width, double length, double height) {
    this.mesh = loadMesh(SHIP_MESH);
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
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,  ambientColour, 0);
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, ambientColour, 0);
      gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
    
      mesh.render(gl);
    }
    gl.glEndList();

    for(Graphic uiElem : ui) {
      gl.glPushMatrix();
      uiElem.init(gl, glu);
      gl.glPopMatrix();
    }
  }
  
  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    for(Graphic uiElem : ui) {
      gl.glPushMatrix();
      uiElem.render(gl, glu, pos, rot);
      gl.glPopMatrix();
    }
    
    gl.glTranslated(pos.x, pos.y, Vector2D.Z);
    gl.glRotated(rot.toDegrees(), rot.x, rot.y, rot.z);
    gl.glCallList(listID);
  }
}
