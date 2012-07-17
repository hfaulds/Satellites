package scene.actors;


import geometry.Box;
import geometry.Mesh;
import geometry.Rotation;
import geometry.Vector2D;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import scene.controllers.ui.Graphic;


import net.msg.ActorMsg;

public abstract class Actor {

  public static final double G = 0.0001;
  
  private static int ID_COUNT = 0;
  public int id = nextID();

  private final float[] ambientColour = { 0.7f, 0.7f, 0.7f };
  
  public final Mesh mesh;
  public final Box boundingbox;

  public final List<Graphic> ui = new ArrayList<Graphic>();
  
  public final Vector2D position;
  public final Vector2D velocity;
  
  public final Rotation rotation;
  public final Rotation spin = new Rotation();

  public final double mass;

  private int listID;

  protected Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh, int id) {
    this.position = new Vector2D(position);
    this.rotation = new Rotation(rotation);
    this.boundingbox = Box.createBoundingBox(mesh);
    this.mass = mass;
    this.velocity = new Vector2D();
    this.mesh = mesh;
    this.id = id;
  }

  public Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh) {
    this(position, rotation, mass, mesh, nextID());
  }
  
  public Actor(double x, double y, double mass, Mesh mesh) {
    this(new Vector2D(x, y), new Rotation(), mass, mesh, nextID());
  }

  public void tick() {
    this.position._add(velocity);
    this.rotation._add(spin);
  }
  
  public void init(GL2 gl, GLU glu) {
    listID = gl.glGenLists(1);
    gl.glNewList(listID, GL2.GL_COMPILE);
    {
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,  ambientColour, 0);
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, ambientColour, 0);
      gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
    
      mesh.render(gl);
    }
    gl.glEndList();

    for(Graphic uiElem : ui) {
      gl.glPushMatrix();
      uiElem.init(gl, glu);
      gl.glPopMatrix();
    }
  }

  public void render(GL2 gl, GLU glu) {
    for(Graphic uiElem : ui) {
      gl.glPushMatrix();
      uiElem.render(gl, glu, position, rotation);
      gl.glPopMatrix();
    }
    
    gl.glTranslated(position.x, position.y, Vector2D.Z);
    gl.glRotated(rotation.toDegrees(), rotation.x, rotation.y, rotation.z);
    gl.glCallList(listID);
  }

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
  public ActorMsg getInfo() {
    return new ActorMsg(position, rotation, id, mass, (Class<Actor>) this.getClass());
  }

  public void _update(ActorMsg info) {
    this.position._set(info.position);
    this.rotation._set(info.rotation);
  }

  protected static int nextID() {
    return ID_COUNT++;
  }

  public static Actor fromInfo(ActorMsg info) {
    try {
      Constructor<Actor> constructor = info.actorClass.getConstructor(Vector2D.class, Rotation.class, double.class, int.class);
      return constructor.newInstance(info.position, info.rotation, info.mass, info.id);
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      System.exit(0);
      return null;
    }
  }
 }
