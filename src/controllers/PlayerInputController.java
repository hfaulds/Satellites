package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;



import math.Vector2D;

import actors.Actor;


public class PlayerInputController implements Controller, KeyListener {

  private static final double ACCELERATION = 0.0005;
  private static final double SPIN = 0.0005;

  private static final Vector2D START_DIRECTION = new Vector2D(0, -1);
  
  private static final char KEY_FORWARD = 'w';
  private static final char KEY_LEFT = 'a';
  private static final char KEY_RIGHT = 'd';
  
  private double spinMag = 0;
  private double accelMag = 0;

  public Actor actor;
  
  public PlayerInputController() {
    
  }
  
  public PlayerInputController(Actor actor) {
    setActor(actor);
  }
  
  public void setActor(Actor actor) {
    this.actor = actor;
    System.out.println("input actor set");
  }
  
  @Override
  public void tick(List<Actor> actors) {
    if(actor != null) {
      System.out.println(accelMag);
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
  public void keyPressed(KeyEvent e) {
    switch(e.getKeyChar()) {
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
    switch(e.getKeyChar()) {
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
  public void keyTyped(KeyEvent arg0) {}
}
