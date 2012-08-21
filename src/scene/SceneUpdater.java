package scene;

import geometry.Box;
import geometry.Vector2D;

import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import scene.actors.Actor;
import scene.actors.ProjectileActor;
import scene.actors.ShipActor;
import scene.actors.StationActor;
import scene.controllers.Controller;

public class SceneUpdater extends MouseAdapter {
  
  private final Scene scene;
  private long lastFrame = System.currentTimeMillis();
  private final Queue<Actor> actorRemoveQueue = new LinkedList<Actor>();

  public SceneUpdater(Scene scene) {
      this.scene = scene;
  }

  public void tick() {
    long dt = System.currentTimeMillis() - lastFrame;
    
    List<Actor> actors = scene.actors;
    
    synchronized(actors) {
      scene.input.tick(dt, actors);
      synchronized(scene.controllers) {
        for(Controller controller : scene.controllers) {
          controller.tick(dt, actors);
        }
      }
    }
    
    int numActors = actors.size();
    for(int i=0; i < numActors; i++) {
      for(int j=i+1; j < numActors; j++) {
        try {
          Actor a = actors.get(i);
          Actor b = actors.get(j);
          if(collisionExists(a, b)) {
            a.velocity._set(new Vector2D(0,0));
            b.velocity._set(new Vector2D(0,0));
          }
        } catch (Exception e) {
          
        }
        
      }
    }
    
    for(Actor actor : actorRemoveQueue) {
      scene.removeActor(actor);
    }
    
    lastFrame = System.currentTimeMillis();
  }

  private boolean collisionExists(Actor a, Actor b) {
    if(Box.boxesIntersect(a.boundingbox, b.boundingbox)) {
      
      /* Ship Projectile Collisions */
      if(a instanceof ProjectileActor && b instanceof ShipActor) {
        ((ShipActor)b).damage(ProjectileActor.DAMAGE);
        actorRemoveQueue.add(a);
      } else if (b instanceof ProjectileActor && a instanceof ShipActor) {
        ((ShipActor)a).damage(ProjectileActor.DAMAGE);
        actorRemoveQueue.add(b);
        
      /* Ship Station Collisions */
      } else if(a instanceof StationActor && b instanceof ShipActor) {
        //TODO contact gui
      } else if (b instanceof StationActor && a instanceof ShipActor) {
        
      }
      
    }
    return false;
  }
}