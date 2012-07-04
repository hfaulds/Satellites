package Actors;

import Graphics.ShipGraphic;
import Math.Vector2D;

public class ShipActor extends Actor {

  private static double MASS = 0.0001;
  
  private static double WIDTH = 0.1;
  private static double HEIGHT = 0.5;
  private static double DEPTH = 0.2;

  private PointGravityActor centerGrav = new PointGravityActor(pos.x, pos.y, MASS);
  
  private PointGravityActor[] cornerGrav = new PointGravityActor[]{
    new PointGravityActor(WIDTH /-2 + pos.x, HEIGHT / 2 + pos.y, MASS),
    new PointGravityActor(WIDTH / 2 + pos.x, HEIGHT / 2 + pos.y, MASS),
    new PointGravityActor(WIDTH /-2 + pos.x, HEIGHT /-2 + pos.y, MASS),
    new PointGravityActor(WIDTH / 2 + pos.x, HEIGHT /-2 + pos.y, MASS)
  };
  
  public ShipActor(double x, double y) {
    super(x, y, MASS );
    this.graphic = new ShipGraphic(WIDTH, HEIGHT, DEPTH);
  }

  @Override
  public void updateVelocity(Actor[] actors) {
    //Calculate Force On Each Corner
    Vector2D[] forces = new Vector2D[cornerGrav.length];
    for(int i=0; i < forces.length; i++)
      forces[i] = new Vector2D();
    
    for(int i=0; i < cornerGrav.length; i++) {
      for(Actor actor : actors) {
        if(actor != this) {
          forces[i]._add(cornerGrav[i].gravForceFrom(actor)); 
        }
      }
    }
  }

  @Override
  public boolean collides(Actor a) {
    return false;
  }

}
