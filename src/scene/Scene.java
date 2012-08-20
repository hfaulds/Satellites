package scene;


import geometry.Vector2D;
import geometry.Vector3D;
import gui.InGameGUI;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.msg.ActorCreateMsg;
import render.gimley.components.ChatBox;
import render.gimley.components.FPSCounter;
import render.gimley.components.GComponent;
import scene.actors.Actor;
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

public class Scene extends MouseAdapter {

  public final PlayerInputController input = new PlayerInputController();
  public final ChatBox messageHandler = new ChatBox(null, new Vector2D(15, 10));

  public final List<Actor>       actors = new ArrayList<Actor>();
  public final List<Controller>  controllers = new ArrayList<Controller>();
  public final PointLightActor[] lights = {new PointLightActor(), new PointLightActor(new Vector3D(-10, 10, 10))};
  public final GComponent[]          ui = new GComponent[]{new FPSCounter(null, new Vector2D(5, InGameGUI.HEIGHT - 50)), messageHandler};

  public final Queue<Actor> actorqueue = new LinkedList<Actor>();
  
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

}
