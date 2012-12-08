package core;

import ingame.actors.PointLightActor;
import ingame.actors.ShipActor;
import ingame.actors.player.PlayerShipActor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.esotericsoftware.kryonet.Connection;

import core.geometry.Vector3D;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.pregame.SceneCreateMsg;

public class Scene {

  public final List<Actor>       actors = new ArrayList<Actor>();
  public final List<Controller>  controllers = new ArrayList<Controller>();
  public final PointLightActor[] lights = {new PointLightActor(), new PointLightActor(new Vector3D(-10, 10, 10))};

  public final Queue<Actor> actorqueue = new LinkedList<Actor>();

  private List<ScenePlayerListener> newPlayerListeners = new LinkedList<ScenePlayerListener>();

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
  
  public void forceAddActor(Actor actor) {
    synchronized(actors) {
      actors.add(actor);
    }
  }

  public void removeActor(Actor actor) {
    synchronized(actors) {
      actors.remove(actor);
    }
  }

  public PlayerShipActor createPlayer(ShipActor ship) {
    PlayerShipActor player = new PlayerShipActor(ship);
    for(ScenePlayerListener listener : newPlayerListeners) {
      listener.playerActorChanged(player);
    }
    return player;
  }
  
  public void populate(SceneCreateMsg msg, Connection connection) {
    for(ActorCreateMsg actorInfo : msg.actorInfoList) {
      Actor actor = Actor.fromInfo(actorInfo);
      
      if(actor.id == msg.playerID) {
        PlayerShipActor player = createPlayer((ShipActor)actor);
        queueAddActor(player);
      } else {
        queueAddActor(actor);
      }
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

  public void addNewPlayerListener(ScenePlayerListener newPlayerListener) {
    this.newPlayerListeners.add(newPlayerListener);
  }
}
