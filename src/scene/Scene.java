package scene;

import graphics.FPSSprite;
import graphics.Sprite;

import java.awt.event.MouseAdapter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import math.Rotation;
import math.Vector2D;
import net.ActorInfo;
import actors.Actor;
import actors.PointLightActor;
import actors.ShipActor;
import controllers.Controller;
import controllers.client.PlayerInputController;

public class Scene extends MouseAdapter {

  public final ShipActor player = new ShipActor(0, 0);
  public final PlayerInputController playerController = new PlayerInputController(player);
  
  public final List<Actor> actors = new ArrayList<Actor>(Arrays.asList(player));
  public final List<Controller> controllers = new ArrayList<Controller>(Arrays.asList(playerController));
  public final PointLightActor[] lights = {new PointLightActor()};
  
  public final Sprite[] ui = new Sprite[]{new FPSSprite()};

  public void addController(Controller controller) {
    controllers.add(controller);
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

  public void addActors(List<ActorInfo> actorInfo) {
    synchronized(actors) {
      for(ActorInfo info : actorInfo) {
        try {
          Constructor<Actor> constructor = info.actorClass.getConstructor(Vector2D.class, Rotation.class, double.class);
          Actor actor = constructor.newInstance(info.position, info.rotation, info.mass);
          actor.id = info.id;
          addActor(actor);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  public Actor findActor(int id) {
    for(Actor actor : actors)
      if(actor.id == id)
        return actor;
    
    return null;
  }
}
