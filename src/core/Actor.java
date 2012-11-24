package core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.geometry.Box;
import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.net.msg.ActorCreateMsg;
import core.net.msg.ActorUpdateMsg;
import core.render.material.Material;

public abstract class Actor {

  public static final double G = 0.000001;
  
  private static int ID_COUNT = 0;
  
  public final int id;
  
  private boolean visible = true;
  
  public final Mesh mesh;
  public final Box boundingbox;
  public final Material material;

  public final List<Actor> subactors = new ArrayList<Actor>();
  
  public final Vector2D position;
  public final Vector2D velocity;
  
  public final Rotation rotation;
  public final Rotation spin = new Rotation();

  public final double mass;

  protected int listID;


  /* CONSTRUCTORS */
  
  protected Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh, boolean visible, int id) {
    this(position, rotation, mass, mesh, visible, id, new Material());
  }
  
  protected Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh) {
    this(position, rotation, mass, mesh, true, NEXT_ID(), new Material());
  }
  
  public Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh, Material material) {
    this(position, rotation, mass, mesh, true, NEXT_ID(), material);
  }
  

  protected Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh, boolean visible, int id, Material material) {
    this.position = position;
    this.rotation = rotation;
    this.boundingbox = Box.createBoundingBox(mesh, this.position);
    this.mass = mass;
    this.velocity = new Vector2D();
    this.mesh = mesh;
    this.id = id;
    this.visible = visible;
    this.material = material;
    ID_COUNT = Math.max(ID_COUNT, id);
  }



  public void add(Actor actor) {
    subactors.add(actor); 
  }
  
  
  /* TICK */

  public void tick(long dt) {
    this.position._add(velocity.mult(dt));
    this.rotation._add(spin.mult(dt));
  }
  
  
  /* RENDERING */
  
  public void init(GL2 gl, GLU glu) {
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      material.startRender(gl);
      mesh.render(gl);
      material.stopRender(gl);
    }
    gl.glEndList();

    for(Actor actor : subactors) {
      gl.glPushMatrix();
      actor.init(gl, glu);
      gl.glPopMatrix();
    }
  }

  public void render(GL2 gl, GLU glu) {
    if(visible) {
      gl.glPushMatrix();
      gl.glTranslated(position.x, position.y, Vector2D.Z);
      gl.glRotated(rotation.toDegrees(), rotation.x, rotation.y, rotation.z);
      gl.glCallList(listID);      
      gl.glPopMatrix();
      
      for(Actor actor : subactors) {
        gl.glPushMatrix();
        actor.render(gl, glu);
        gl.glPopMatrix();
      }
      
    }
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
    
    double f_mag = G * this.mass * actor.mass / Math.pow(direction.magnitude(), 2);
    
    return direction._normalize().mult(f_mag);
  }

  public Vector2D gravForceFrom(Actor actor) {
	return gravForceFrom(actor, new Vector2D());
  }
  
  
  /* SYNCING*/

  @SuppressWarnings("unchecked")
  public ActorCreateMsg getCreateMsg() {
    return new ActorCreateMsg(position, rotation, visible, id, mass, (Class<Actor>) getClass());
  }
  
  public ActorUpdateMsg getUpdateMsg() {
    return new ActorUpdateMsg(position, rotation, id);
  }

  public void _update(ActorUpdateMsg info) {
    this.position._set(info.position);
    this.rotation._set(info.rotation);
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public static Actor fromInfo(ActorCreateMsg info) {
    try {
      Constructor<? extends Actor> constructor = info.actorClass.getConstructor(Vector2D.class, Rotation.class, double.class, boolean.class, int.class);
      return constructor.newInstance(info.position, info.rotation, info.mass, info.visible, info.id);
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      System.exit(0);
      return null;
    }
  }
  
  /* IDs */
  
  private static int NEXT_ID() {
    return ID_COUNT + 1;
  }

 }
