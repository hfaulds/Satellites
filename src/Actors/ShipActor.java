package Actors;

import Graphics.ShipGraphic;
import Math.Vector2D;

public class ShipActor extends PointGravityActor {

  private static double MASS    = 0.0001;
  
  private static double WIDTH   = 0.02;
  private static double LENGTH  = 1.0;
  private static double HEIGHT  = 0.1;
  
  private Vector2D[] corners = new Vector2D[]{
    new Vector2D(WIDTH /-2, LENGTH / 2),
    new Vector2D(WIDTH / 2, LENGTH / 2),
    new Vector2D(WIDTH /-2, LENGTH /-2),
    new Vector2D(WIDTH / 2, LENGTH /-2)
  };
  
  public ShipActor(double x, double y) {
    this(x, y, 0, 0);
  }

  public ShipActor(double x, double y, double vx, double vy) {
    super(x, y, MASS);
    this.graphic = new ShipGraphic(WIDTH, LENGTH, HEIGHT);
  }

  @Override
  public void updateVelocity(Actor[] actors) {
    //Calculate Force On Each Corner
    Vector2D   force            = new Vector2D();
    Vector2D[] cornerForces     = new Vector2D[corners.length];
    Vector2D[] cornerOffsets    = new Vector2D[corners.length];
    
    for(int i=0; i < cornerForces.length; i++) {
      cornerForces[i]   = new Vector2D();
      cornerOffsets[i]  = corners[i]._rotate(this.rotation.mag);
    }
    
    for(Actor actor : actors) {
      if(actor != this) {
        force._add(gravForceFrom(actor));
        
        for(int i=0; i < corners.length; i++) {
          cornerForces[i]._add(gravForceFrom(actor, cornerOffsets[i])); 
        }
      }
    }

    this.applyForce(force);
    
    double torque = 0;

    for(int i=0; i < cornerForces.length; i++)
    {
      Vector2D F        = cornerForces[i];
      double r          = cornerOffsets[i].magnitude();
      
      double theta      = Math.acos(Vector2D.dot(cornerOffsets[i].normalized(), F.normalized()));
      
      double moment = r * F.magnitude() * Math.sin(theta);
      
      torque -= moment * 300;
    }

    this.spin += torque;
  }
  
  @Override
  public boolean collides(Actor a) {
    return false;
  }

}
