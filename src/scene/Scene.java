package scene;

import gui.components.sprites.FPSSprite;
import gui.components.sprites.MsgSprite;
import gui.components.sprites.Sprite;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import scene.actors.Actor;
import scene.actors.PointLightActor;
import scene.controllers.ClientShipController;
import scene.controllers.Controller;
import scene.controllers.PlayerInputController;
import scene.graphics.ship.ShipControlSprite;
import scene.graphics.ship.ShipDirectionSprite;
import scene.graphics.ship.ShipGraphic;

import net.msg.ActorMsg;

import com.esotericsoftware.kryonet.Connection;


public class Scene extends MouseAdapter {

  public final PlayerInputController input = new PlayerInputController();

  public final List<Actor>          actors = new ArrayList<Actor>();
  public final List<Controller>     controllers = new ArrayList<Controller>();
  public final PointLightActor[]    lights = {new PointLightActor()};

  public final MsgSprite messageHandler = new MsgSprite();
  public final Sprite[] ui = new Sprite[]{new FPSSprite(), messageHandler};
  
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
  
  public void populate(List<ActorMsg> actorInfo, int playerID, Connection connection) {
    for(ActorMsg info : actorInfo) {
      Actor actor = Actor.fromInfo(info);
      if(info.id == playerID) {
        addController(new ClientShipController(actor, connection));
        addPlayer(actor);
      }
      addActor(actor);
    }
  }
  
  public Actor findActor(int id) {
    for(Actor actor : actors)
      if(actor.id == id)
        return actor;
    
    return null;
  }

}
