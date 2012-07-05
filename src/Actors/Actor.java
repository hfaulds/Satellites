package Actors;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import Controllers.Controller;
import Graphics.Graphic;
import Math.Rotation;
import Math.Vector2D;

public abstract class Actor {

  public static final double G = 0.0001;
  
  protected Graphic graphic;
  public Controller<? extends Actor> controller;
  
  public final double mass;
  
  public final Vector2D position;
  public final Vector2D velocity;
  
  public final Rotation rotation = new Rotation();
  public double spin = 0;
  
  public Actor(double x, double y, double mass) {
    this(new Vector2D(x, y), new Vector2D(), mass);
  }
  
  public Actor(double x, double y, double vx, double vy, double mass) {
    this(new Vector2D(x, y), new Vector2D(vx, vy), mass);
  }
  
  public Actor(Vector2D pos, Vector2D vel, double mass) {
    this.position = pos;
    this.velocity = vel;
    this.mass = mass;
  }

  public void render(GL2 gl, GLU glu) {
    this.graphic.render(gl, glu, position, rotation);
  }

  public void updateVelocity(Actor[] actors) {}
  
  public void updatePosition() {
    this.position._add(velocity);
    this.rotation._add(spin);
  }
  
  public abstract boolean collides(Actor a);

  public void applyForce(Vector2D force) {
    this.velocity._add(force.divide(mass));
  }
  
  public void applyTorque(double torque) {
    this.spin += torque;
  }
 }
