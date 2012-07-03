package Actors;

import Graphics.ShipGraphic;

public class ShipActor extends Actor {

  private static double MASS = 0.0001;

  public ShipActor(double x, double y) {
    super(x, y, MASS );
    this.graphic = new ShipGraphic();
  }

}
