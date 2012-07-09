package actors;

import graphics.ship.ShipGraphic;
import math.Rotation;
import math.Vector2D;
import controllers.Controller;

public class ShipActor extends Actor {

  private static double MASS    = 0.0001;
  
  public static double WIDTH   = 0.02;
  public static double LENGTH  = 1.0;
  public static double HEIGHT  = 0.1;

  public ShipActor(Vector2D position, Rotation rotation, double mass) {
    super(position, rotation, mass, new ShipGraphic(WIDTH, LENGTH, HEIGHT));
  }
  
  public ShipActor(double x, double y) {
    this(x, y, 0, 0);
  }

  public ShipActor(double x, double y, double vx, double vy) {
    this(x, y, vx, vy, null);
  }

  public ShipActor(double x, double y, double vx, double vy, Controller controller) {
    super(x, y, MASS, new ShipGraphic(WIDTH, LENGTH, HEIGHT));
  }

  @Override
  public boolean collides(Actor a) {
    return false;
  }

}
