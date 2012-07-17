package scene;

import geometry.Box;
import geometry.Vector2D;

import java.awt.event.MouseAdapter;
import java.util.List;

import scene.actors.Actor;
import scene.controllers.Controller;

public class SceneUpdater extends MouseAdapter {
  
  private final Scene scene;
  private long lastFrame = System.currentTimeMillis();

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
        Actor a = actors.get(i);
        Actor b = actors.get(j);
        if(collisionExists(a, b)) {
          a.velocity._set(new Vector2D(0,0));
          b.velocity._set(new Vector2D(0,0));
        }
      }
    }
    lastFrame = System.currentTimeMillis();
  }

  private boolean collisionExists(Actor a, Actor b) {
    if(Box.boxesIntersect(a.boundingbox, b.boundingbox)) {
      System.out.println();
      System.out.println(a.getClass());
      System.out.println(b.getClass());
    }
    return false;
  }
}