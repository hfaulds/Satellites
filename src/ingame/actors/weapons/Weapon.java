package ingame.actors.weapons;

import java.util.LinkedList;
import java.util.List;

import ingame.controllers.PlayerInputController;
import ingame.items.AmmoItem;
import ingame.items.ItemListener;

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
  private PlayerInputController input;

  private List<ItemListener> listeners = new LinkedList<ItemListener>();
  private AmmoItem ammo;
  
  public Weapon(Vector2D position, Vector2D mountPoint, Rotation shipRotation, Mesh mesh, AmmoItem ammo) {
    super(new ActorInfo(position, new Rotation(), 0, mesh));
    this.mountPoint = mountPoint;
    this.shipRotation = shipRotation;
    this.collideable = false;
    this.ammo = ammo;
  }
  
  @Override
  public void render(GL2 gl, GLU glu) {
    if(input != null) {
      this.rotation.mag = Rotation.XRotFromVector(input.aimDirection).mag;
    }
    gl.glTranslated(position.x, position.y, Vector2D.Z);
    gl.glRotated(shipRotation.toDegrees(), rotation.x, rotation.y, rotation.z);
    gl.glTranslated(mountPoint.x, mountPoint.y, Vector2D.Z);
    gl.glRotated(rotation.toDegrees() - shipRotation.toDegrees(), rotation.x, rotation.y, rotation.z);
    gl.glCallList(renderer.getRenderID());
  }
  
  public abstract String getName();

  public void setInput(PlayerInputController controller) {
    this.input = controller;
  }
  
  public boolean fire() {
    boolean fired = ammo.remove(1);
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
}
