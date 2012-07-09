package scene;

import graphics.Sprite;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import actors.Actor;
import actors.ShipActor;
import controllers.Controller;
import controllers.PlayerInputController;

public class Scene extends MouseAdapter {

  public final ShipActor player = new ShipActor(0, 0);
  public final PlayerInputController playerController = new PlayerInputController(player);
  
  public final List<Actor> actors = new ArrayList<Actor>(Arrays.asList(
    new Actor[]{
       player, 
       //new SatelliteActor( -5,  1),
       //new SatelliteActor(-10, -1),
       //new SatelliteActor(  -8,  -5, 10),
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

  public void removeActor(Actor actor) {
    actors.remove(actor);
  }
}
