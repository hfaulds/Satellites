package scene;


import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import scene.actors.Actor;
import scene.actors.PointLightActor;
import scene.actors.ShipActor;
import scene.controllers.ClientShipController;
import scene.controllers.Controller;
import scene.controllers.PlayerInputController;
import scene.graphics.ship.ShipControlGraphic;
import scene.graphics.ship.ShipDirectionGraphic;
import scene.graphics.ship.ShipGraphic;
import scene.graphics.ship.ShipHealthGraphic;
import scene.ui.FPSSprite;
import scene.ui.MsgSprite;
import scene.ui.Sprite;

import net.msg.ActorMsg;

import com.esotericsoftware.kryonet.Connection;


public class Scene extends MouseAdapter {

  public final PlayerInputController input = new PlayerInputController();
  public final MsgSprite messageHandler = new MsgSprite();

  public final List<Actor>       actors = new ArrayList<Actor>();
  public final List<Controller>  controllers = new ArrayList<Controller>();
  public final PointLightActor[] lights = {new PointLightActor()};
  public final Sprite[]          ui = new Sprite[]{new FPSSprite(), messageHandler};


  public final Queue<Actor>       actorqueue = new LinkedList<Actor>();
  
  public final String username;
  
  public Scene(String username) {
    this.username = username;
  }
  
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

  public void queueAddActor(Actor actor) {
    synchronized(actors) {
      actorqueue.add(actor);
    }
  }

  public void removeActor(Actor actor) {
    synchronized(actors) {
      actors.remove(actor);
    }
  }

  public void addPlayer(ShipActor player) {
    input.setActor(player);
    ShipGraphic graphic = (ShipGraphic)player.graphic;
    graphic.ui.add(new ShipControlGraphic());
    graphic.ui.add(new ShipDirectionGraphic());
    graphic.ui.add(new ShipHealthGraphic(player));
    queueAddActor(player);
  }
  
  public void populate(List<ActorMsg> actorInfo, int playerID, Connection connection) {
    for(ActorMsg info : actorInfo) {
      Actor actor = Actor.fromInfo(info);
      if(info.id == playerID) {
        addController(new ClientShipController(actor, connection));
        addPlayer((ShipActor)actor);
      }
      queueAddActor(actor);
    }
  }
  
  public Actor findActor(int id) {
    for(Actor actor : actors)
      if(actor.id == id)
        return actor;
    
    return null;
  }

}
