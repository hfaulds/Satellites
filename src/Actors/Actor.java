package Actors;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Graphics.Graphic;

public abstract class Actor {

  protected Graphic graphic;
  
  public final double mass;
  
  public double x;
  public double y;
  
  public Actor(double x, double y, double mass) {
    this.x = x;
    this.y = y;
    this.mass = mass;
  }

  public void render(GL2 gl, GLU glu) {
    this.graphic.render(gl, glu, x, y);
  }

  public void updateVelocity(Actor[] satellites) {
  }

  public void updatePosition() {
  }

}
