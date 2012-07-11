package actors;

import graphics.SatelliteGraphic;
import math.Rotation;
import math.Vector2D;

public class SatelliteActor extends Actor {

  public final static double density = 0.05;
  
  private double radius = calcRadius(mass);
  
  public SatelliteActor(Vector2D position, Rotation rotation, double mass, int id) {
    super(position, rotation, mass, new SatelliteGraphic(calcRadius(mass)), id);
  }
  
  public SatelliteActor(double x, double y, double mass) {
    super(x, y, mass, new SatelliteGraphic(calcRadius(mass)));
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
