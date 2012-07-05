package Graphics.UI;

import javax.media.opengl.GL2;

import Math.Vector2D;

public abstract class UIComponent {
  
  protected Vector2D position;
  
  public UIComponent(Vector2D position) {
    this.position = position;
  }

  public abstract void render(GL2 gl);
}
