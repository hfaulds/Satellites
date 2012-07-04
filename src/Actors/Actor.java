package Actors;


import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Graphics.Graphic;
import Math.Rotation;
import Math.Vector2D;

public abstract class Actor {

  public static final double G = 0.0001;
  
  protected Graphic graphic;
  
  public final double mass;
  
  public final Vector2D pos;
  public final Rotation rot;
  
  public Actor(double x, double y, double mass) {
    this(new Vector2D(x, y), new Rotation(), mass);
  }
  
  public Actor(Vector2D pos, Rotation rot, double mass) {
    this.pos = pos;
    this.rot = rot;
    this.mass = mass;
  }

  public void render(GL2 gl, GLU glu) {
    this.graphic.render(gl, glu, pos, rot);
  }

  public void updateVelocity(Actor[] actors) {}
  public void updatePosition() {}
  
  public abstract boolean collides(Actor a);
 }
