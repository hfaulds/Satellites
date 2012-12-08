package core;

import ingame.collisions.ShipProjectileCollisionListener;
import ingame.collisions.SimpleBounceCollisionListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import core.collisions.Collision;
import core.collisions.CollisionHandler;
import core.collisions.CollisionListener;
import core.geometry.Box;

public class SceneUpdater {
  
  private final Scene scene;
  private long lastFrame = System.currentTimeMillis();
  
  public final Queue<Actor> actorRemoveQueue = new LinkedList<Actor>();
  
  private final List<CollisionListener> listeners = new LinkedList<CollisionListener>();
  public final CollisionHandler collisionHandler = new CollisionHandler(listeners);
  
  public SceneUpdater(Scene scene) {
    this.scene = scene;
    this.addCollisionListener(new ShipProjectileCollisionListener(this));
    this.addCollisionListener(new SimpleBounceCollisionListener());
  }
  
  public void addCollisionListener(CollisionListener listener) {
    listeners.add(listener);
  }

  public void tick() {
    long dt = System.currentTimeMillis() - lastFrame;
    
    List<Actor> actors = scene.actors;
    synchronized(scene.controllers) {
      for(Controller controller : scene.controllers) {
        synchronized(scene.actors){ 
          controller.tick(dt, actors);
        }
      }
    }
    
    synchronized(actors) {
      int numActors = actors.size();
      for(int i=0; i < numActors; i++) {
        for(int j=i+1; j < numActors; j++) {
          Actor a = actors.get(i);
          Actor b = actors.get(j);
          findCollision(a, b);
        }
      }
      
      collisionHandler.tick();
      
      for(Actor actor : actorRemoveQueue) {
        scene.removeActor(actor);
      }
    }
    
    lastFrame = System.currentTimeMillis();
  }

  private void findCollision(Actor a, Actor b) {
    //TODO: Do not like this
    if(collisionExists(a, b)) {
      collisionHandler.addOrUpdateCollision(new Collision(a, b));
    }

    if(a.subactors.size() > 0) {
      for(Actor a2 : a.subactors) {
        findCollision(a2, b);
      }
    }
    
    if(b.subactors.size() > 0) {
      for(Actor b2 : b.subactors) {
        findCollision(a, b2);
      }
    }
  }

  private boolean collisionExists(Actor a, Actor b) {
    return a.collideable && b.collideable && Box.boxesIntersect(a.boundingbox, b.boundingbox);
  }

}