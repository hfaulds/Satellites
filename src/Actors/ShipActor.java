package Actors;

import Graphics.ShipGraphic;

public class ShipActor extends Actor {

  private static double MASS = 0.0001;
  
  private static double WIDTH = 0.2;
  private static double HEIGHT = 0.5;
  private static double DEPTH = 0.2;

  private PointGravityActor gravActor = new PointGravityActor(x, y, MASS);
  private PointGravityActor[] gravActors = new PointGravityActor[4]{
    new PointGravityActor(WIDTH/-2 + x, HEIGHT/2 + y,  MASS),
    new PointGravityActor(WIDTH/2 + x, HEIGHT/2 + y,  MASS),
    new PointGravityActor(WIDTH/-2 + x, HEIGHT/-2 + y, MASS),
    new PointGravityActor(WIDTH/2 + x, HEIGHT/-2 + y, MASS)
  }
  
  public ShipActor(double x, double y) {
    super(x, y, MASS );
    this.graphic = new ShipGraphic(WIDTH, HEIGHT, DEPTH);
  }

  @Override
  public void updateVelocity(Actor[] actors) {
    //Calculate Force On Each Corner
    Vector2D[] forces = new Vector2D[gravActors.length];
    for(int i=0; i < gravActors.length; i++) {
      for(Actor actor : actors) {
        forces[i]._add(gravActor[i].gravForceFrom(actor)); 
      }
    }
  }

  @Override
  public boolean collides(Actor a) {
    return false;
  }

}
