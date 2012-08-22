package scene;


import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import core.geometry.Box;
import core.geometry.Vector2D;


import scene.actors.Actor;
import scene.collisions.Collision;
import scene.collisions.CollisionListener;
import scene.collisions.ShipProjectileCollisionHandle;
import scene.controllers.Controller;

public class SceneUpdater extends MouseAdapter {
  
  private final Scene scene;
  private long lastFrame = System.currentTimeMillis();
  
  public final Queue<Actor> actorRemoveQueue = new LinkedList<Actor>();

  private List<CollisionListener> listeners = new LinkedList<CollisionListener>();
  
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
            a.velocity._set(new Vector2D(0,0));
            b.velocity._set(new Vector2D(0,0));
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
      for(CollisionListener listener : listeners) {
        if(checkTypes(a, b, listener)) {
          listener.collision(new Collision(a, b, listener.getTypes()));
        }
      }
    }
    return false;
  }

  private boolean checkTypes(Actor a, Actor b, CollisionListener listener) {
    Class<? extends Actor>[] types = listener.getTypes();
    boolean a1 =  a.getClass().equals(types[0]);
    boolean b1 =  b.getClass().equals(types[1]);
    
    boolean a2 =  a.getClass().equals(types[1]);
    boolean b2 =  b.getClass().equals(types[0]);
    
    return a1 && b1 || a2 && b2;
  }
}