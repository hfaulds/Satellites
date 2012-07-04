package Actors;

import Math.Vector2D;

public class PointGravityActor extends Actor {

  public PointGravityActor(double x, double y, double mass) {
    super(x, y, mass);
  }
 
  public Vector2D gravForceFrom(Actor actor) {
    Vector2D direction = actor.position.sub(this.position);
    
    double f_mag = G * this.mass * actor.mass / Math.pow(direction.magnitude(), 2);
    
    return direction._normalize().multiply(f_mag);
  }

  public boolean collides(Actor actor) {
    return false;
  }
  
}

