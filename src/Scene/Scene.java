package Scene;

import java.awt.event.MouseAdapter;

import Actors.Actor;
import Actors.PlayerActor;
import Actors.SatelliteActor;
import Graphics.UI.UIComponent;
 
public class Scene extends MouseAdapter {

  public final Actor player   = new PlayerActor(5, 6, 0, -0.01);
  
  public final Actor[] actors = {
     player, 
     //new SatelliteActor( -5,  1),
    // new SatelliteActor(-10, -1),
     new SatelliteActor(  -8,  -5, 10),
     //new SatelliteActor(  0,  5, -.02,   0,  5),
     //new SatelliteActor(  0, -5,  .02 ,  0,  5),
  };
  
  public final UIComponent[] ui = new UIComponent[]{
      player.ui
  };
  
  public void tick() {
    player.controller.tick(actors);
    
    for(Actor actor : actors)
      actor.updateVelocity(actors);
    
    for(Actor actor : actors)
      actor.updatePosition();
  }
}