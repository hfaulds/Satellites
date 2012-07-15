package scene.ui;

import geometry.Vector2D;

import javax.media.opengl.GL2;



public abstract class Sprite {
  
  protected Vector2D position;
  
  public Sprite(Vector2D position) {
    this.position = position;
  }

  public abstract void render(GL2 gl);
}
