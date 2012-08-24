package core;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import scene.actors.PointLightActor;
import scene.actors.ShipActor;
import scene.controllers.ClientShipController;
import scene.controllers.Controller;
import scene.controllers.PlayerInputController;
import scene.controllers.ui.ShipAimGraphic;
import scene.controllers.ui.ShipControlGraphic;
import scene.controllers.ui.ShipDirectionGraphic;
import scene.controllers.ui.ShipHealthGraphic;

import com.esotericsoftware.kryonet.Connection;

import core.geometry.Vector3D;
import core.net.msg.ActorCreateMsg;

public class Scene extends MouseAdapter {

  public final String username;
  public Actor player;
  
  public final PlayerInputController input = new PlayerInputController();

  public final List<Actor>       actors = new ArrayList<Actor>();
  public final List<Controller>  controllers = new ArrayList<Controller>();
  public final PointLightActor[] lights = {new PointLightActor(), new PointLightActor(new Vector3D(-10, 10, 10))};

  public final Queue<Actor> actorqueue = new LinkedList<Actor>();

  
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
    this.player = player;
    input.setActor(player);
    player.ui.add(new ShipControlGraphic());
    player.ui.add(new ShipDirectionGraphic());
    player.ui.add(new ShipHealthGraphic(player));
    player.ui.add(new ShipAimGraphic(input));
    queueAddActor(player);
  }
  
  public void populate(List<ActorCreateMsg> actorInfo, int playerID, Connection connection) {
    for(ActorCreateMsg info : actorInfo) {
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

  public void destroy() {
    actors.clear();
    for(Controller controller : controllers)
      controller.destroy();
    controllers.clear();
  }
}
