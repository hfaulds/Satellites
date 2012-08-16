package scene.controllers;

import geometry.Vector2D;

import java.util.List;

import net.NetworkConnection;
import render.Renderer3D;
import scene.actors.Actor;
import scene.actors.ProjectileActor;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

public class PlayerInputController extends MouseAdapter implements Controller, KeyListener {

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

  public Actor actor;

  private NetworkConnection connection;
  public long timeTillNextFire = 0;
  
  public Vector2D aimDirection = START_DIRECTION;
  
  public void setActor(Actor actor) {
    this.actor = actor;
  }
  
  public void setConnection(NetworkConnection connection) {
    this.connection = connection;
  }
  
  @Override
  public void tick(long dt, List<Actor> actors) {
    
    if(timeTillNextFire > 0)
      timeTillNextFire -= dt;
    
    if(actor != null) {
      if(accelMag != 0) {
        Vector2D acceleration = START_DIRECTION.rotate(actor.rotation.mag)._mult(accelMag * ACCELERATION);
        actor.velocity._add(acceleration);
      }
      if(spinMag != 0) {
        double spin = spinMag * SPIN;
        actor.spin._add(spin); 
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
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

  @Override
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
  
  @Override
  public void mouseMoved(MouseEvent e) {
    this.aimDirection = Renderer3D.project(actor.position).sub(new Vector2D(e.getX(), e.getY()))._normalize()._invertX();
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    if(e.getButton() == FIRE_BUTTON && timeTillNextFire <= 0) {
      if(connection != null) {
        Vector2D position = actor.position.add(aimDirection.mult(2));
        ProjectileActor projectile = new ProjectileActor(position, aimDirection, actor.velocity);
        
        connection.fireProjectile(projectile);
        
        timeTillNextFire = GUN_COOLDOWN;
      }
    }
  }
}
