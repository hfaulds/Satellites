package Scene;

import java.awt.event.MouseAdapter;

import Actors.Actor;
import Controllers.Controller;
 
public class SceneUpdater extends MouseAdapter {

  private final Scene scene;

  public SceneUpdater(Scene scene) {
      this.scene = scene;
  }

  public void tick() {
    for(Controller controller : scene.controllers)
      controller.tick(scene.actors);
    
    for(Actor actor : scene.actors)
      actor.updateVelocity(scene.actors);
    
    for(Actor actor : scene.actors)
      actor.updatePosition();
  }
}
