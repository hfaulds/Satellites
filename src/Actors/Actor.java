package Actors;


import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Graphics.Graphic;
import Math.Vector2D;

public abstract class Actor {

  public static final double G = 0.0001;
  
  protected Graphic graphic;
  
  public final double mass;
  
  public Vector2D position;
  
  public Actor(double x, double y, double mass) {
    this.position = new Vector2D(x,y);
    this.mass = mass;
  }

  public void render(GL2 gl, GLU glu) {
    this.graphic.render(gl, glu, position.x, position.y);
  }

  public void updateVelocity(Actor[] satellites) {}
  public void updatePosition() {}
  
  public abstract boolean collides(Actor a);
  
  public static Vector2D gravityBetween(Actor a, Actor b) {
  
    Vector2D direction = b.position.sub(a.position);
    
    double f_mag = G * a.mass * b.mass / Math.pow(direction.magnitude(), 2);
    
    return direction._normalize().multiply(f_mag);
  }
}
