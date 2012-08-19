package render.ging;

import geometry.Vector2D;
import geometry.Vector3D;

import javax.media.opengl.GL2;

public abstract class GComponent {
  
  protected Vector3D position;
  
  public GComponent(Vector2D position) {
    this.position = new Vector3D(position);
  }

  public abstract void render(GL2 gl);
}
