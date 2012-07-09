package scene;

import java.awt.event.MouseAdapter;

import controllers.Controller;

import actors.Actor;

 
public class SceneUpdater extends MouseAdapter {

  private final Scene scene;

  public SceneUpdater(Scene scene) {
      this.scene = scene;
  }

  public void tick() {
    for(Controller controller : scene.controllers)
      controller.tick(scene.actors);
    
    for(Actor actor : scene.actors)
      actor.tick();
  }
}
