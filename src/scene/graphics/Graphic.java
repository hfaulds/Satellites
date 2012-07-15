package scene.graphics;

import geometry.Rotation;
import geometry.Vector2D;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;



public interface Graphic {
  public void init(GL2 gl, GLU glu);
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot);
}
