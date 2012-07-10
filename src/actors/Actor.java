package actors;

import graphics.Graphic;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import math.Rotation;
import math.Vector2D;
import net.ActorInfo;

public abstract class Actor {

  public static final double G = 0.0001;
  
  private static int ID = 0;
  public int id = nextID();
  
  public final Graphic graphic;
  
  public final Vector2D position;
  public final Vector2D velocity;
  
  public final Rotation rotation;
  public final Rotation spin = new Rotation();

  public final double mass;

  public Actor(Vector2D position, Rotation rotation, double mass, Graphic graphic) {
    this.position = new Vector2D(position);
    this.rotation = new Rotation(rotation);
    this.mass = mass;
    this.velocity = new Vector2D();
    this.graphic = graphic;
  }
  
  public Actor(double x, double y, double mass, Graphic graphic) {
    this(new Vector2D(x, y), new Rotation(), mass, graphic);
  }
  
  
  public void init(GL2 gl, GLU glu) {
    this.graphic.init(gl, glu);
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

  public static int nextID() {
    return ++ID;
  }

 }
