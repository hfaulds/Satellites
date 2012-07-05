package Scene;

import Actors.Actor;
import Actors.SatelliteActor;
import Actors.ShipActor;
 
public class Scene {
  
  public final Actor[] actors = new Actor[]{
      //new SatelliteActor( -5,  1),
      
      //new SatelliteActor(-10, -1),
      
     new SatelliteActor(  -8,  -5, 10),
     //new SatelliteActor(  0,  5, -.02,   0,  5),
     //new SatelliteActor(  0, -5,  .02 ,  0,  5),

     new ShipActor(5, 6)
  };
  
  public void tick() {
    for(Actor actor : actors)
      actor.updateVelocity(actors);
    
    for(Actor actor : actors)
      actor.updatePosition();
  }
}