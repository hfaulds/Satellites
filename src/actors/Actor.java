package actors;

import graphics.Graphic;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import net.ActorInfo;

import math.Rotation;
import math.Vector2D;

public abstract class Actor {

  public static final double G = 0.0001;
  private static int TOTAL_ACTORS = 0;
  public final int id = TOTAL_ACTORS++;
  
  protected Graphic graphic;
  
  public final Vector2D position;
  public final Vector2D velocity;
  
  public final Rotation rotation;
  public final Rotation spin = new Rotation();

  public final double mass;

  public Actor(Vector2D position, Rotation rotation, double mass) {
    this.position = position;
    this.rotation = rotation;
    this.mass = mass;
    this.velocity = new Vector2D();
  }
  
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
    this.rotation = new Rotation();
  }

  public void render(GL2 gl, GLU glu) {
    this.graphic.render(gl, glu, position, rotation);
  }

  public void tick() {
    this.position._add(velocity);
    this.rotation._add(spin);
  }
  
  public abstract boolean collides(Actor a);

  public void applyForce(Vector2D force) {
    this.velocity._add(force.divide(mass));
  }
  
  public void applyTorque(Rotation torque) {
    this.spin._add(torque);
  }
  
  public Vector2D gravForceFrom(Actor actor, Vector2D offset) {
    Vector2D direction = actor.position.sub(this.position.add(offset));
    
    double f_mag = G * this.mass * actor.mass / Math.pow(direction.magnitude(), 2);
    
    return direction._normalize().mult(f_mag);
  }

  public Vector2D gravForceFrom(Actor actor) {
	return gravForceFrom(actor, new Vector2D());
  }

  @SuppressWarnings("unchecked")
  public ActorInfo getInfo() {
    return new ActorInfo(position, rotation, id, mass, (Class<Actor>) this.getClass());
  }

  public void _update(ActorInfo info) {
    this.position._set(info.position);
    this.rotation._set(info.rotation);
  }
 }
