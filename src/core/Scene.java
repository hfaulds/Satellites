package core;

import ingame.actors.PlayerShipActor;
import ingame.actors.PointLightActor;
import ingame.actors.ShipActor;
import ingame.controllers.ClientShipController;
import ingame.controllers.PlayerInputController;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.esotericsoftware.kryonet.Connection;

import core.geometry.Vector3D;
import core.net.msg.ActorCreateMsg;
import core.net.msg.SceneCreateMsg;

public class Scene extends MouseAdapter {

  public final PlayerInputController input = new PlayerInputController();

  public final List<Actor>       actors = new ArrayList<Actor>();
  public final List<Controller>  controllers = new ArrayList<Controller>();
  public final PointLightActor[] lights = {new PointLightActor(), new PointLightActor(new Vector3D(-10, 10, 10))};

  public final Queue<Actor> actorqueue = new LinkedList<Actor>();

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

  public PlayerShipActor makePlayer(ShipActor ship) {
    PlayerShipActor player = new PlayerShipActor(ship);
    input.setPlayer(player);
    return player;
  }
  
  public void populate(SceneCreateMsg info, Connection connection) {
    for(ActorCreateMsg actorInfo : info.actorInfoList) {
      Actor actor = Actor.fromInfo(actorInfo);
      
      if(actorInfo.id == info.playerID ) {
        addController(new ClientShipController(actor, connection));
        actor = makePlayer((ShipActor)actor);
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
