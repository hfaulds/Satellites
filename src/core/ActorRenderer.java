package core;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ActorRenderer {
  
  private int renderID;
  
  private boolean visible = true;
  public final Mesh mesh;

  public ActorRenderer(Mesh mesh) {
    this.mesh = mesh;
  }

  public void init(GL2 gl, GLU glu) {
    renderID = gl.glGenLists(1);
    gl.glNewList(renderID, GL2.GL_COMPILE);
    {
      mesh.material.startRender(gl);
      mesh.render(gl);
      mesh.material.stopRender(gl);
    }
    gl.glEndList();
  }

  public void render(GL2 gl, Vector2D position, Rotation rotation) {
    if(visible) {
      gl.glPushMatrix();
      gl.glTranslated(position.x, position.y, Vector2D.Z);
      gl.glRotated(rotation.toDegrees(), rotation.x, rotation.y, rotation.z);
      gl.glCallList(renderID);      
      gl.glPopMatrix();
    }
  }

  public int getRenderID() {
    return renderID;
  }
  
  public void setVisible(boolean visibility) {
    this.visible = visibility;
  }
}