package ingame.actors.weapons;

import ingame.items.AmmoItem;
import ingame.items.ItemListener;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.Actor;
import core.ActorInfo;
import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public abstract class Weapon extends Actor {

  private final Rotation shipRotation;

  private final Vector2D mountPoint;

  private List<ItemListener> listeners = new LinkedList<ItemListener>();
  private AmmoItem ammo;

  private long timeTillNextFire;
  
  public Weapon(Vector2D position, Vector2D mountPoint, Rotation shipRotation, Mesh mesh, AmmoItem ammo) {
    super(new ActorInfo(position, new Rotation(), 0, mesh));
    this.mountPoint = mountPoint;
    this.shipRotation = shipRotation;
    this.collideable = false;
    this.ammo = ammo;
  }
  
  @Override
  public void tick(long dt) {
    if(timeTillNextFire > 0)
      timeTillNextFire -= dt;
  }
  
  @Override
  public void render(GL2 gl, GLU glu) {
    gl.glTranslated(position.x, position.y, Vector2D.Z);
    gl.glRotated(shipRotation.toDegrees(), rotation.x, rotation.y, rotation.z);
    gl.glTranslated(mountPoint.x, mountPoint.y, Vector2D.Z);
    gl.glRotated(rotation.toDegrees() - shipRotation.toDegrees(), rotation.x, rotation.y, rotation.z);
    gl.glCallList(renderer.getRenderID());
  }
  
  public abstract String getName();
  public abstract long getCoolDown();

  public boolean fire() {
    boolean fired = ammo.remove(1);
    timeTillNextFire = getCoolDown();
    if(ammo.getQuantity() == 0) {
      for(ItemListener listener : listeners)
        listener.itemDepleted(ammo);
    }
    return fired;
  }
  
  public void setItemListener(ItemListener listener) {
    listeners.add(listener);
  }

  public AmmoItem getAmmo() {
    return ammo;
  }

  public long getTimeTillNextFire() {
    return timeTillNextFire;
  }

}
