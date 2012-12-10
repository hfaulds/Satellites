package core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.geometry.Box;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.ingame.ActorUpdateMsg;

public abstract class Actor {

  private static final double SYNC_ROTATION_THRESHOLD = 0.3;
  private static final int SYNC_DISTANCE_THRESHOLD = 2;
  private static final double G = 0.000001;

  public final double mass;
  public boolean moveable = true;
  
  public final Vector2D position;
  public final Vector2D velocity = new Vector2D();
  
  public final Rotation rotation;
  public final Rotation spin = new Rotation();
  
  public final int id;
  public final List<Actor> subactors = new ArrayList<Actor>();

  public ActorRenderer renderer;
  public final Box boundingbox;
  public boolean collideable = true;
  

  /* CONSTRUCTORS */
  
  protected Actor(ActorInfo info) {
    this.position = info.position;
    this.rotation = info.rotation;
    this.mass = info.mass;
    this.boundingbox = Box.createBoundingBox(info.mesh, this.position);
    this.renderer = new ActorRenderer(info.mesh);
    this.id = info.id;
  }

  
  
  /* TICK */

  public void tick(long dt) {
    if(moveable) {
      this.position._add(velocity.mult(dt));
      this.rotation._add(spin.mult(dt));
    }
  }

  public void freeze() {
    this.velocity._set(0,0);
    this.spin.mag = 0;
    moveable = false;
  }
  
  public void unFreeze() {
    moveable = true;
  }
  
  /* RENDERING */

  public void setVisible(boolean visibility) {
    this.renderer.setVisible(visibility);
  }
  
  public void init(GL2 gl, GLU glu) {
    renderer.init(gl, glu);
    
    for(Actor actor : subactors) {
      actor.init(gl, glu);
    }
  }

  public void render(GL2 gl, GLU glu) {
    renderer.render(gl, position, rotation);
    for(Actor actor : subactors) {
      gl.glPushMatrix();
      actor.render(gl, glu);
      gl.glPopMatrix();
    }
  }

  protected void add(Actor actor) {
    subactors.add(actor); 
  }
  
  /* FORCES */
  
  public void applyForce(Vector2D force) {
    this.velocity._add(force.divide(mass));
  }
  
  public void applyTorque(Rotation torque) {
    this.spin._add(torque);
  }
  
  public Vector2D gravForceFrom(Actor actor, Vector2D offset) {
    Vector2D direction = actor.position.sub(this.position.add(offset));
    double forceMagnitude = G * this.mass * actor.mass / Math.pow(direction.magnitude(), 2);
    return direction._normalize().mult(forceMagnitude);
  }

  public Vector2D gravForceFrom(Actor actor) {
    return gravForceFrom(actor, new Vector2D());
  }
  
  
  /* SYNCING*/

  @SuppressWarnings("unchecked")
  public ActorCreateMsg getCreateMsg() {
    return new ActorCreateMsg(position, rotation, id, mass, (Class<Actor>) getClass(), renderer.mesh.name);
  }
  
  public ActorUpdateMsg getUpdateMsg() {
    return new ActorUpdateMsg(position, rotation, id);
  }

  public void _update(ActorUpdateMsg info) {
    if(this.position.distanceTo(info.position) > SYNC_DISTANCE_THRESHOLD) {
      this.position._set(info.position);
    }
    if(info.rotation.mag > SYNC_ROTATION_THRESHOLD) {
      this.rotation._set(info.rotation);
    }
  }

  public static Actor fromInfo(Class<? extends Actor> actorClass, Vector2D position, Rotation rotation, double mass, int id, String mesh) {
    try {
      Constructor<? extends Actor> constructor = actorClass.getConstructor(Vector2D.class, Rotation.class, double.class, int.class, String.class);
      return constructor.newInstance(position, rotation, mass, id, mesh);
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      System.exit(0);
      return null;
    }
  }
  
  public static Actor fromMsg(ActorCreateMsg info) {
    return fromInfo(info.actorClass, info.position, info.rotation, info.mass, info.id, info.mesh);
  }

 }
