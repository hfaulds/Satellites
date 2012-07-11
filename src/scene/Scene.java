package scene;

import graphics.FPSSprite;
import graphics.Sprite;
import graphics.ship.ShipControlSprite;
import graphics.ship.ShipDirectionSprite;
import graphics.ship.ShipGraphic;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.ActorInfo;
import actors.Actor;
import actors.PointLightActor;

import com.esotericsoftware.kryonet.Connection;

import controllers.ClientShipController;
import controllers.Controller;
import controllers.PlayerInputController;

public class Scene extends MouseAdapter {
  
  public final PlayerInputController input = new PlayerInputController();
  
  public final List<Actor>          actors = new ArrayList<Actor>();
  public final List<Controller>     controllers = new ArrayList<Controller>(Arrays.asList(input));
  public final PointLightActor[]    lights = {new PointLightActor()};
  public final Sprite[]             ui = new Sprite[]{new FPSSprite()};

  public void addController(Controller controller) {
    synchronized(controllers) {
      controllers.add(controller);
    }
  }
  
  public void removeController(Controller controller) {
    synchronized(controllers) {
      controllers.remove(controller);
    }
  }

  public void addActor(Actor actor) {
    synchronized(actors) {
      actors.add(actor);
    }
  }

  public void removeActor(Actor actor) {
    synchronized(actors) {
      actors.remove(actor);
    }
  }

  public void addPlayer(Actor player) {
    input.setActor(player);
    ShipGraphic graphic = (ShipGraphic)player.graphic;
    graphic.ui.add(new ShipControlSprite());
    graphic.ui.add(new ShipDirectionSprite());
    addActor(player);
  }
  
  public Actor populate(List<ActorInfo> actorInfo, ActorInfo playerInfo, Connection connection) {
    Actor player = Actor.fromInfo(playerInfo);
    addPlayer(player);
    addController(new ClientShipController(player, connection));
    
    for(ActorInfo info : actorInfo) {
      addActor(Actor.fromInfo(info));
    }
    
    return player;
  }
  
  public Actor findActor(int id) {
    for(Actor actor : actors)
      if(actor.id == id)
        return actor;
    
    return null;
  }

}
