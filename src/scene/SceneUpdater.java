package scene;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import scene.collisions.Collision;
import scene.collisions.CollisionHandler;
import scene.collisions.CollisionListener;
import scene.collisions.ShipProjectileCollisionHandle;
import scene.controllers.Controller;
import core.geometry.Box;

public class SceneUpdater {
  
  private final Scene scene;
  private long lastFrame = System.currentTimeMillis();
  
  public final Queue<Actor> actorRemoveQueue = new LinkedList<Actor>();
  
  private final List<CollisionListener> listeners = new LinkedList<CollisionListener>();
  public final CollisionHandler collisionHandler = new CollisionHandler(listeners);
  
  public SceneUpdater(Scene scene) {
    this.scene = scene;
    this.addCollisionListener(new ShipProjectileCollisionHandle(this));
  }
  
  public void addCollisionListener(CollisionListener listener) {
    listeners.add(listener);
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
          Actor a = actors.get(i);
          Actor b = actors.get(j);
          if(collisionExists(a, b)) {
            collisionHandler.addOrUpdateCollision(new Collision(a, b));
          }
      }
    }
    
    collisionHandler.tick();
    
    for(Actor actor : actorRemoveQueue) {
      scene.removeActor(actor);
    }
    
    lastFrame = System.currentTimeMillis();
  }

  private boolean collisionExists(Actor a, Actor b) {
    return Box.boxesIntersect(a.boundingbox, b.boundingbox);
  }

}