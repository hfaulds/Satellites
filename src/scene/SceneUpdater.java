package scene;

import geometry.Vector2D;

import java.awt.event.MouseAdapter;
import java.util.List;

import scene.actors.Actor;
import scene.actors.SatelliteActor;
import scene.actors.ShipActor;
import scene.controllers.Controller;

public class SceneUpdater extends MouseAdapter {
  
  private final CollisionHandle ShipSat = new CollisionHandle(ShipActor.class, SatelliteActor.class);
  private final CollisionHandle SatSat = new CollisionHandle(SatelliteActor.class, SatelliteActor.class);
  private final CollisionHandle ShipShip = new CollisionHandle(ShipActor.class, ShipActor.class);
  
  private final Scene scene;

  public SceneUpdater(Scene scene) {
      this.scene = scene;
  }

  public void tick() {
    
    List<Actor> actors = scene.actors;
    
    synchronized(actors) {
      scene.input.tick(actors);
      synchronized(scene.controllers) {
        for(Controller controller : scene.controllers) {
          controller.tick(actors);
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
    
  }

  private boolean collisionExists(Actor a, Actor b) {
    //AABB collisions first
    
    CollisionHandle handle = new CollisionHandle(a.getClass(), b.getClass());
    
    if(handle.equal(ShipSat)) {
      
    } else if(handle.equal(SatSat)) {
      SatelliteActor aa = (SatelliteActor)a;
      SatelliteActor bb = (SatelliteActor)b;
      double distance = a.position.distanceTo(b.position);
      return distance <= aa.radius + bb.radius;
    } else if(handle.equal(ShipShip)) {
      
    }
    
    return false;
  }
  
  private class CollisionHandle {
    
    private Class<?> a;
    private Class<?> b;
    
    public CollisionHandle(Class<?> a, Class<?> b) {
      this.a = a;
      this.b = b;
    }
    
    public boolean equal(CollisionHandle other) {
      return (other.a.equals(b) && other.b.equals(a)) || (other.a.equals(a) && other.b.equals(b));
    }
  }
}