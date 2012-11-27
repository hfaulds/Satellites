package ingame.controllers;

import ingame.actors.ProjectileActor;

import java.util.List;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import core.Actor;
import core.Controller;
import core.geometry.Vector2D;
import core.net.connections.NetworkConnection;

public class PlayerInputController implements Controller {

  private static final Vector2D START_DIRECTION = new Vector2D(0, -1);
  public static final long GUN_COOLDOWN = 1000;
  
  private static final int KEY_FORWARD = 'W';
  private static final int KEY_LEFT    = 'A';
  private static final int KEY_RIGHT   = 'D';
  
  private static final double ACCELERATION = 0.00001;
  private static final double SPIN         = 0.00001;

  public static int FIRE_BUTTON = MouseEvent.BUTTON1;
  
  private double accelMag = 0;
  private double spinMag  = 0;

  public Vector2D aimDirection = START_DIRECTION;
  
  public Actor actor;

  private NetworkConnection connection;
  public long timeTillNextFire = 0;
  
  public void setActor(Actor actor) {
    this.actor = actor;
  }
  
  public void setConnection(NetworkConnection connection) {
    this.connection = connection;
  }
  
  @Override
  public void tick(long dt, List<Actor> actors) {
    
    if(timeTillNextFire > 0) {
      timeTillNextFire -= dt;
    }
    
    if(actor != null) {
      if(accelMag != 0) {
        Vector2D acceleration = START_DIRECTION.rotate(actor.rotation.mag)._multiply(dt * accelMag * ACCELERATION);
        actor.velocity._add(acceleration);
      }
      if(spinMag != 0) {
        double spin = dt * spinMag * SPIN;
        actor.spin._add(spin); 
      }
    }
  }

  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KEY_FORWARD:
        accelMag = 1;
        break;
      case KEY_LEFT:
        spinMag = 1;
        break;
      case KEY_RIGHT:
        spinMag = -1;
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KEY_FORWARD:
        accelMag = 0;
        break;
      case KEY_LEFT:
        if(spinMag == 1)
          spinMag = 0;
        break;
      case KEY_RIGHT:
        if(spinMag == -1)
          spinMag = 0;
        break;
    }
  }
  
  public void mouseMoved(Vector2D mouse, int width, int height) {
    if(actor != null) {
      aimDirection = mouse.sub(new Vector2D(width, height).divide(2))._normalize();
    }
  }

  
  public void mouseReleased(Vector2D click) {
    if(actor != null && timeTillNextFire <= 0) {
      if(connection != null) {
        Vector2D position = actor.position.add(aimDirection.mult(2));
        ProjectileActor projectile = new ProjectileActor(position, aimDirection, actor.velocity, actor);
        
        connection.fireProjectile(projectile);
        
        timeTillNextFire = GUN_COOLDOWN;
      }
    }
  }

  @Override
  public void destroy() {
    this.actor = null;
  }
  
}
