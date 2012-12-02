package core;

import ingame.actors.PointLightActor;
import ingame.actors.ShipActor;
import ingame.actors.player.PlayerShipActor;
import ingame.controllers.ClientShipController;

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

  public final List<Actor>       actors = new ArrayList<Actor>();
  public final List<Controller>  controllers = new ArrayList<Controller>();
  public final PointLightActor[] lights = {new PointLightActor(), new PointLightActor(new Vector3D(-10, 10, 10))};

  public final Queue<Actor> actorqueue = new LinkedList<Actor>();

  private List<NewPlayerListener> newPlayerListeners = new LinkedList<NewPlayerListener>();

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
    for(NewPlayerListener listener : newPlayerListeners) {
      listener.newPlayer(player);
    }
    return player;
  }
  
  public void populate(SceneCreateMsg info, Connection connection) {
    for(ActorCreateMsg actorInfo : info.actorInfoList) {
      Actor actor = Actor.fromInfo(actorInfo);
      
      if(actorInfo.id == info.playerID ) {
        actor = makePlayer((ShipActor)actor);
        addController(new ClientShipController(actor, connection));
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

  public void addNewPlayerListener(NewPlayerListener newPlayerListener) {
    this.newPlayerListeners .add(newPlayerListener);
  }
}
