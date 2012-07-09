package scene;

import graphics.Sprite;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import actors.Actor;
import actors.SatelliteActor;
import actors.ShipActor;
import controllers.Controller;
import controllers.PlayerController;

public class Scene extends MouseAdapter {

  public final Actor player = new ShipActor(5, 6, 0, -0.01);
  public final PlayerController playerController = new PlayerController(player);
  
  public final List<Actor> actors = new ArrayList<Actor>(Arrays.asList(
    new Actor[]{
       player, 
       //new SatelliteActor( -5,  1),
       //new SatelliteActor(-10, -1),
       new SatelliteActor(  -8,  -5, 10),
       //new SatelliteActor(  0,  5, -.02,   0,  5),
       //new SatelliteActor(  0, -5,  .02 ,  0,  5),
      }
    ));
  
  public final List<Controller> controllers = new ArrayList<Controller>(Arrays.asList(
      new Controller[]{
          playerController
        }
      ));
  
  public final Sprite[] ui = new Sprite[]{
      //player.ui
  };

  public void addController(Controller controller) {
    controllers.add(controller);
  }

  public void addActor(Actor actor) {
    actors.add(actor);
  }
}
