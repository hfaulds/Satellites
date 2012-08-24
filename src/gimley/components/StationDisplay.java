package gimley.components;

import gimley.core.components.GComponent;
import gimley.core.components.TopBar;
import gimley.core.components.button.GButton;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.render.Renderer2D;

public class StationDisplay extends GComponent {

  private static final int WIDTH = 750;
  private static final int HEIGHT = 600;
  
  public final GButton undock = new GButton(this, new Vector2D(675, 10), 65, 20, "UNDOCK");
  
  public StationDisplay(GComponent parent) {
    super(parent, new Vector2D(20, 20));
    
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

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawFillRect(gl, position.x + 10, position.y + 130, 730, HEIGHT - 140);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawFillRect(gl, position.x + 10, position.y + 40, 730, 80);
    
    super.render(gl, width, height);
  }
}
