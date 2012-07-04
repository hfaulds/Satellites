package Actors;

import Graphics.SatelliteGraphic;
import Math.Vector2D;

public class SatelliteActor extends PointGravityActor {
  
  public final static double density = 50;
  
  public Vector2D velocity;
  
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
    Vector2D accel = new Vector2D();
  
    for(Actor actor : actors) {
      if(actor != this) {
        if(!this.collides(actor)) {
          /*Acelleration from gravitational pull*/
          accel._add(gravForceFrom(actor).divide(mass));
        } else {
          /*Collision*/
          this.velocity._multiply(0.9);
          // V = [dx dy]
          // N = 
          //Vnew = b * ( -2*(V dot N)*N + V )
        }
      }
    }

    /*Acceleration*/
    this.velocity._add(accel);
  }
  
  public void updatePosition() {
    this.pos._add(velocity);
  }

  @Override
  public boolean collides(Actor other) {
    return other.pos.distanceTo(this.pos) <= radius*2;
  }
}
