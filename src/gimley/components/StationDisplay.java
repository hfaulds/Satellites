package gimley.components;

import gimley.components.button.GButton;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.render.Renderer2D;

public class StationDisplay extends GComponent {

  private static final int WIDTH = 750;
  private static final int HEIGHT = 500;
  
  public final GButton undock = new GButton(this, new Vector2D(650, 5), 65, 25, "UNDOCK");
  
  public StationDisplay(GComponent parent) {
    super(parent, new Vector2D(20, 20));
    
    setVisible(false);
    
    this.width = WIDTH;
    this.height = HEIGHT;
    
    subcomponents.add(undock);
    subcomponents.add(new TopBar(this, "Station"));
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y, WIDTH, HEIGHT);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x, position.y, WIDTH, HEIGHT, 0.9f);
    super.render(gl, width, height);

  }
}
