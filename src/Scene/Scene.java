package Scene;

import Actors.Actor;
import Actors.SatelliteActor;
import Actors.ShipActor;
 
public class Scene {
  
  public final Actor[] actors = new Actor[]{
      new SatelliteActor( -5,  1),
      
      new SatelliteActor(-10, -1),
      
      new SatelliteActor(  0,  0, 10),
      new SatelliteActor(  0,  2, -.02,   0,  3),
      new SatelliteActor(  0, -2,  .02 ,  0,  3),
      
      //new ShipActor( 3, 4)
  };
  
  public void tick() {
    for(Actor actor : actors) {
      actor.updateVelocity(actors);
    }
    for(Actor actor : actors) {
      actor.updatePosition();
    }
  }
}