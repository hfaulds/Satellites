package Actors;

import Math.Vector2D;

public class PointGravityActor extends Actor {

  public PointGravityActor(double x, double y, double mass) {
    super(x, y, mass);
  }

  public Vector2D gravForceFrom(Actor actor, Vector2D offset) {
    Vector2D direction = actor.position.sub(this.position.add(offset));
    
    double f_mag = G * this.mass * actor.mass / Math.pow(direction.magnitude(), 2);
    
    return direction._normalize().mult(f_mag);
  }

  public Vector2D gravForceFrom(Actor actor) {
    return gravForceFrom(actor, new Vector2D());
  }

  public boolean collides(Actor actor) {
    return false;
  }
  
}

