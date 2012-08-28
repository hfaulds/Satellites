package core.gimley.components;

import core.geometry.Vector2D;

public class GPanel extends GComponent {

  public GPanel(GComponent parent, String title, Vector2D position, int width, int height) {
    super(parent, position, width, height);
    add(new GTopBar(this, title));
  }

}