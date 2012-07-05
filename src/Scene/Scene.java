package Scene;

import java.awt.event.MouseAdapter;

import Actors.Actor;
import Actors.SatelliteActor;
import Actors.ShipActor;
import Math.Vector2D;
 
public class Scene extends MouseAdapter {
  
  public final Actor player             = new ShipActor(5, 6, 0, -0.01);
  
  public final Actor[] actors           = new Actor[]{
     this.player, 
     new SatelliteActor( -5,  1),
      
     new SatelliteActor(-10, -1),
      
     new SatelliteActor(  -8,  -5, 10),
     new SatelliteActor(  0,  5, -.02,   0,  5),
     new SatelliteActor(  0, -5,  .02 ,  0,  5),

  };
  
  public boolean mouseDown             = false;
  public Vector2D mousePosition      = new Vector2D();
  
  public void tick() {
    /*Need to project the player and calculate the difference from mouse to player*/
    if(mouseDown)
      player.applyForce(mousePosition._normalize()._mult(0.0000001));
    
    for(Actor actor : actors)
      actor.updateVelocity(actors);
    
    for(Actor actor : actors)
      actor.updatePosition();
  }
}