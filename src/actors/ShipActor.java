package actors;

import graphics.Graphic;
import graphics.ship.ShipGraphic;
import math.Rotation;
import math.Vector2D;

public class ShipActor extends Actor {

  private static double MASS    = 0.0001;
  
  public static double WIDTH   = 0.02;
  public static double LENGTH  = 1.0;
  public static double HEIGHT  = 0.1;

  public ShipActor(Vector2D position, Rotation rotation, double mass, int id) {
    super(position, rotation, mass, getGraphic(), id);
  }

  public ShipActor(double x, double y) {
    super(x, y, MASS, getGraphic());
  }

  private static Graphic getGraphic() {
    return new ShipGraphic(WIDTH, LENGTH, HEIGHT);
  }
  
  @Override
  public boolean collides(Actor a) {
    return false;
  }

}
