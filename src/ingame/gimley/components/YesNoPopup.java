package ingame.gimley.components;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.gimley.components.GPanel;
import core.render.Renderer2D;

public class YesNoPopup extends GPanel {

  public static final int HEIGHT = 150;
  public static final int WIDTH = 250;
  
  private final String message;

  public YesNoPopup(GComponent parent, String title, String message, Vector2D position) {
    super(parent, title, position, WIDTH, HEIGHT);
    this.message = message;
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y, 
        this.width, this.height);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x, position.y, 
        this.width, this.height, 0.9f);
    
    Renderer2D.drawText(gl, position.x + 5, position.y + this.height - 25, message);
    
    super.render(gl, width, height);
  }

}