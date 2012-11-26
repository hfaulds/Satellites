package core.render;

import core.geometry.Vector2D;

public class Camera {
  
  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 20;
  
  public double zoom = ZOOM_DEFAULT;
  
  public boolean bZoom = false;
  
  private final Vector2D position;
  public double ratio;

  public Camera(Vector2D position, int width, int height) {
    this.position = position;
    setRatio(width, height);
  }
  
  public void setRatio(int width, int height) {
    this.ratio = (double) (height/width);
  }

  public void zoomOut(int change) {
    this.zoom = Math.max(Math.abs(this.zoom - change * ZOOM_RATE), ZOOM_RATE);
    this.bZoom = true;
  }

  public Vector2D getPosition() {
    return position;
  }
}