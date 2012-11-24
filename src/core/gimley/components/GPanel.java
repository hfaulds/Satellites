package core.gimley.components;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.render.Renderer2D;

public class GPanel extends GComponent {

  public GPanel(GComponent parent, String title, Vector2D position, int width, int height) {
    super(parent, position, width, height);
    add(new GTopBar(this, title));
  }
  
  public void render(GL2 gl, int width, int height) {
    
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y, 
        this.width, this.height);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x, position.y, 
        this.width, this.height, 0.9f);
    
    super.render(gl, width, height);
  }
  
}