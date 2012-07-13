package scene.graphics;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import math.Rotation;
import math.Vector2D;


public interface Graphic {
  public void init(GL2 gl, GLU glu);
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot);
}
