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
  
  private final Rotation aimRotation;
  private final Rotation shipRotation;
  
  public Canon(Vector2D position, Rotation aimRotation, Rotation shipRotation) {
    super(position, aimRotation, 0, MESH);
    this.aimRotation = aimRotation;
    this.shipRotation = shipRotation;
  }
  
  @Override
  public void render(GL2 gl, GLU glu) {
    gl.glTranslated(position.x, position.y, Vector2D.Z);
    gl.glRotated(shipRotation.toDegrees(), aimRotation.x, aimRotation.y, aimRotation.z);
    gl.glTranslated(OFFSET.x, OFFSET.y, Vector2D.Z);
    gl.glRotated(aimRotation.toDegrees() - shipRotation.toDegrees(), aimRotation.x, aimRotation.y, aimRotation.z);
    
    gl.glCallList(listID);
  }
}
