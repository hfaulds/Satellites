package core.render;

import core.geometry.Vector2D;

public class NullCamera extends Camera {

  public NullCamera(int width, int height) {
    super(new Vector2D(), width, height);
  }

}
