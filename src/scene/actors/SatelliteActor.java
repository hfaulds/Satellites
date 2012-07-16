package scene.actors;

import geometry.Rotation;
import geometry.Vector2D;
import scene.graphics.SatelliteGraphic;

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
    double radius = Math.pow( (3.0 * volume) / (4.0 * Math.PI) , (1.0 / 3.0) );
    return radius;
  }
  
  @Override
  public boolean collides(Actor other) {
    return other.position.distanceTo(this.position) <= radius;
  }
}
