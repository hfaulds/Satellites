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
  private static final long FIRE_COOLDOWN = 1000;
  
  private static final int KEY_FORWARD = 'W';
  private static final int KEY_LEFT    = 'A';
  private static final int KEY_RIGHT   = 'D';
  
  private static final double ACCELERATION = 0.00005;
  private static final double SPIN         = 0.00005;

  public static int FIRE_BUTTON = MouseEvent.BUTTON1;
  
  private double accelMag = 0;
  private double spinMag  = 0;

  public Actor actor;

  private NetworkConnection connection;
  private long lastFired = System.currentTimeMillis();
  
  public void setActor(Actor actor) {
    this.actor = actor;
  }
  
  public void setConnection(NetworkConnection connection) {
    this.connection = connection;
  }
  
  @Override
  public void tick(long dt, List<Actor> actors) {
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
  public void mouseClicked(MouseEvent e) {
    if(e.getButton() == FIRE_BUTTON && System.currentTimeMillis() - lastFired > FIRE_COOLDOWN) {
      Vector2D direction = Renderer3D.project(actor.position).sub(new Vector2D(e.getX(), e.getY()))._normalize()._invertX();
      Vector2D position = actor.position.add(direction.mult(2));
      ProjectileActor projectile = new ProjectileActor(position, direction);
      
      if(connection != null) {
        connection.addActor(projectile);
      }
      
      lastFired = System.currentTimeMillis();
    }
  }
}
