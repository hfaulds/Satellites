package Scene;

import java.awt.event.MouseAdapter;

import Actors.Actor;
import Actors.ShipActor;
import Actors.SatelliteActor;
import Controllers.Controller;
import Controllers.PlayerController;
import Graphics.Sprite;
 
public class Scene extends MouseAdapter {

  public final Actor player = new ShipActor(5, 6, 0, -0.01);
  public final PlayerController playerController = new PlayerController(player);
  
  {
	  player.addController(playerController);
  }
  
  public final Actor[] actors = {
     player, 
     //new SatelliteActor( -5,  1),
     //new SatelliteActor(-10, -1),
     new SatelliteActor(  -8,  -5, 10),
     //new SatelliteActor(  0,  5, -.02,   0,  5),
     //new SatelliteActor(  0, -5,  .02 ,  0,  5),
  };
  
  public final Controller[] controllers = {
    playerController
  };
  
  public final Sprite[] ui = new Sprite[]{
      //player.ui
  };
}
