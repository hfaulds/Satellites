package scene;

import graphics.Sprite;

import java.awt.event.MouseAdapter;

import controllers.Controller;
import controllers.KeyboardController;

import actors.Actor;
import actors.SatelliteActor;
import actors.ShipActor;

 
public class Scene extends MouseAdapter {

  public final Actor player = new ShipActor(5, 6, 0, -0.01);
  public final KeyboardController playerController = new KeyboardController(player);
  
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
