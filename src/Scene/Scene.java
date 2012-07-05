package Scene;

import java.awt.event.MouseAdapter;

import Actors.Actor;
import Actors.PlayerActor;
import Actors.SatelliteActor;
import Graphics.SceneUI;
 
public class Scene extends MouseAdapter {

  public final SceneUI ui       = new SceneUI();
  
  public final Actor player     = new PlayerActor(5, 6, 0, -0.01);
  
  public final Actor[] actors   = new Actor[]{
     player, 
     new SatelliteActor( -5,  1),
     new SatelliteActor(-10, -1),
     new SatelliteActor(  -8,  -5, 10),
     new SatelliteActor(  0,  5, -.02,   0,  5),
     new SatelliteActor(  0, -5,  .02 ,  0,  5),
  };

  
  public void tick() {
    player.controller.tick(actors);
    
    for(Actor actor : actors)
      actor.updateVelocity(actors);
    
    for(Actor actor : actors)
      actor.updatePosition();
  }
}