package actors;

import graphics.SatelliteGraphic;
import math.Rotation;
import math.Vector2D;

public class SatelliteActor extends Actor {

  private static double MASS    = 0.1;
  public final static double density = 0.05;
  
  private double radius = calcRadius(mass);
  

  public SatelliteActor(Vector2D position, Rotation rotation, double mass) {
    super(position, rotation, mass);
    this.graphic = new SatelliteGraphic(radius);
  }
  
  public SatelliteActor(double x, double y) {
    this(x, y, MASS);
  }
  
  public SatelliteActor(double x, double y, double mass) {
    this(x, y, 0, 0, mass);
  }
  
  public SatelliteActor(double x, double y, double vx, double vy, double mass) {
    super(x, y, vx, vy, mass);
    this.graphic = new SatelliteGraphic(radius);
  }

  private static double calcRadius(double mass) {
    double volume = mass / density;
    return Math.pow( (3.0 * volume) / (4.0 * Math.PI) , (1.0 / 3.0) );
  }
  
  @Override
  public boolean collides(Actor other) {
    return other.position.distanceTo(this.position) <= radius;
  }
}
