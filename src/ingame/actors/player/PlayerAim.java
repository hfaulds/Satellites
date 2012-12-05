package ingame.actors.player;

import ingame.actors.weapons.WeaponActor;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.Actor;
import core.ActorInfo;
import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class PlayerAim extends Actor {

  public static final double CONTROL_RADIUS     = (double) 2 + 1.5;
  private static final int CIRCLE_SAMPLES       = 300;
  private static final double CIRCLE_INCREMENT  = 2*Math.PI/CIRCLE_SAMPLES;
  private static final float LINE_WIDTH         = 2.5f;
  private static final double LINE_LENGTH       = 0.075;

  private final int mid = - CIRCLE_SAMPLES / 4;
  
  private final PlayerShipActor player;
  
  public PlayerAim(PlayerShipActor player) {
    super(new ActorInfo(player.position, new Rotation(), 0, new Mesh()));
    this.collideable = false;
    this.player = player;
  }
  
  @Override
  public void render(GL2 gl, GLU glu) {
    this.rotation.mag = Rotation.XRotFromVector(player.getAimDirection()).mag;
    
    WeaponActor currentWeapon = player.getCurrentWeapon();
    double l = currentWeapon.getCoolDown() - currentWeapon.getTimeTillNextFire();
    double length = l / currentWeapon.getCoolDown();
    
    int start = mid - (int)(CIRCLE_SAMPLES * LINE_LENGTH * length);
    int end   = mid + (int)(CIRCLE_SAMPLES * LINE_LENGTH * length);
    
    gl.glPushMatrix();
    {
      gl.glDisable(GL2.GL_LIGHTING);
      gl.glTranslated(position.x, position.y, Vector2D.Z);
      gl.glRotated(rotation.toDegrees(), rotation.x, rotation.y, rotation.z);
      
      gl.glColor4d(1.0, 1.0, 1.0, 1.0);
      gl.glLineWidth(LINE_WIDTH);
      
      gl.glBegin(GL2.GL_LINE_STRIP);
      for(int i=start; i < end ; i++){
        gl.glVertex3d(
            Math.cos(i * CIRCLE_INCREMENT) * CONTROL_RADIUS, 
            Math.sin(i * CIRCLE_INCREMENT) * CONTROL_RADIUS,
            Vector2D.Z
          );
      }
      gl.glEnd();

      gl.glEnable(GL2.GL_LIGHTING);
    }
    gl.glPopMatrix();
    
    super.render(gl, glu);
  }
  
}
