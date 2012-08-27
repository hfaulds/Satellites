package core.gimley;

import javax.media.opengl.GL2;

import core.gimley.components.GComponent;
import core.render.Renderer2D;

public class GPopup extends GComponent {

  public GPopup(GComponent parent) {
    super(parent);
    this.width = 200;
    this.height = 150;
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(1, 1, 1, 0.1);
    
    int xstart = width/2 - this.width/2;
    int xend = xstart + this.width;
    
    Renderer2D.drawFillRect(gl, 0, 0, xstart, height);
    Renderer2D.drawFillRect(gl, xend, 0, width, height);

    int ystart = height/2 - this.height/2;
    int yend = ystart + this.height;
    Renderer2D.drawFillRect(gl, xstart, 0, this.width, ystart);
    Renderer2D.drawFillRect(gl, xstart, yend, this.width, height);
  }

}
