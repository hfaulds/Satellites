package scene;

import java.awt.event.MouseAdapter;

import controllers.Controller;

public class SceneUpdater extends MouseAdapter {

  private final Scene scene;

  public SceneUpdater(Scene scene) {
      this.scene = scene;
  }

  public void tick() {
    scene.input.tick(scene.actors);
    synchronized(scene.controllers) {
      for(Controller controller : scene.controllers) {
        controller.tick(scene.actors);
      }
    }
  }
}