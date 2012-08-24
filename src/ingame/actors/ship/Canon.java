package ingame.actors.ship;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class Canon extends Actor {

  public static final Mesh MESH = MeshLoader.loadMesh("Canon-Mk2.obj");
  public static final Vector2D OFFSET = new Vector2D(0, 0.26142); //0.26142
  
  public Canon(Actor parent) {
    super(parent, parent.position, parent.rotation, 0, MESH);
  }
  
  @Override
  public void render(GL2 gl, GLU glu) {
    Rotation aimRot = parent.rotation;
    double shipAngle = parent.parent.rotation.toDegrees();
    
    gl.glTranslated(position.x, position.y, Vector2D.Z);
    gl.glRotated(shipAngle, aimRot.x, aimRot.y, aimRot.z);
    gl.glTranslated(OFFSET.x, OFFSET.y, Vector2D.Z);
    gl.glRotated(aimRot.toDegrees() - shipAngle, aimRot.x, aimRot.y, aimRot.z);
    
    gl.glCallList(listID);
  }
}
