package Actors;

import Graphics.SatelliteGraphic;
import Math.Vector2D;

public class SatelliteActor extends PointGravityActor {
  
  public final static double density = 50;
  
  public final Vector2D velocity;
  
  private double radius;
  
  public SatelliteActor(double x, double y) {
    this(x, y, 0.1);
  }
  
  public SatelliteActor(double x, double y, double mass) {
    this(x, y, 0, 0, mass);
  }
  
  public SatelliteActor(double x, double y, double vx, double vy, double mass) {
    super(x, y, mass);
    
    this.velocity = new Vector2D(vx, vx);

    this.radius = calcRadius(mass);
    this.graphic = new SatelliteGraphic(radius);
  }

  private static double calcRadius(double mass) {
    double volume = mass / density;
    return Math.pow( (3.0 * volume) / (4.0 * Math.PI) , (1.0 / 3.0) );
  }
  
  public void updateVelocity(Actor[] actors) {
    Vector2D force = new Vector2D();
  
    for(Actor actor : actors) {
      if(actor != this) {
        if(!this.collides(actor)) {
          force._add(gravForceFrom(actor));
        } else {
          this.velocity._mult(0.9);
        }
      }
    }

    this.applyForce(force);
  }

  @Override
  public boolean collides(Actor other) {
    return other.position.distanceTo(this.position) <= radius*2;
  }
}
