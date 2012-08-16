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

import net.msg.ActorCreateMsg;
import net.msg.ActorUpdateMsg;
import scene.controllers.ui.Graphic;

public abstract class Actor {

  public static final double G = 0.000001;
  
  private static int ID_COUNT = 0;
  public final int id;

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

  /* CONSTRUCTORS */
  
  protected Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh, int id) {
    this.position = new Vector2D(position);
    this.rotation = new Rotation(rotation);
    this.boundingbox = Box.createBoundingBox(mesh, this.position);
    this.mass = mass;
    this.velocity = new Vector2D();
    this.mesh = mesh;
    this.id = id;
    ID_COUNT = Math.max(ID_COUNT, id);
  }

  protected Actor(Vector2D position, Rotation rotation, double mass, Mesh mesh) {
    this(position, rotation, mass, mesh, NEXT_ID());
  }
  
  protected Actor(double x, double y, double mass, Mesh mesh) {
    this(new Vector2D(x, y), new Rotation(), mass, mesh, NEXT_ID());
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
    return new ActorCreateMsg(position, rotation, id, mass, (Class<Actor>) this.getClass());
  }
  
  public ActorUpdateMsg getUpdateMsg() {
    return new ActorUpdateMsg(position, rotation, id);
  }

  public void _update(ActorUpdateMsg info) {
    this.position._set(info.position);
    this.rotation._set(info.rotation);
  }

  public static Actor fromInfo(ActorCreateMsg info) {
    try {
      Constructor<? extends Actor> constructor = info.actorClass.getConstructor(Vector2D.class, Rotation.class, double.class, int.class);
      return constructor.newInstance(info.position, info.rotation, info.mass, info.id);
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      System.exit(0);
      return null;
    }
  }
  
  private static int NEXT_ID() {
    return ID_COUNT + 1;
  }

 }
