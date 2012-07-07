package Graphics;

import javax.media.opengl.GL2;

import Math.Vector2D;

public abstract class Sprite {
  
  protected Vector2D position;
  
  public Sprite(Vector2D position) {
    this.position = position;
  }

  public abstract void render(GL2 gl);
}