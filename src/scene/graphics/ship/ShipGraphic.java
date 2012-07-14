package scene.graphics.ship;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import math.Rotation;
import math.Vector2D;
import scene.geometry.Mesh;
import scene.geometry.MeshLoader;
import scene.graphics.Graphic;


public class ShipGraphic implements Graphic {

  private final float[] ambientColour   = { 0.7f, 0.7f, 0.7f };

  public final List<Graphic> ui = new ArrayList<Graphic>();
  
  private int listID;
  
  public Mesh mesh;

  public ShipGraphic(double width, double length, double height) {

    try {
      this.mesh = MeshLoader.loadOBJ("Ship-Mk2.obj");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
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
