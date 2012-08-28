package core.gimley;

import javax.media.opengl.GL2;

import core.gimley.components.GComponent;
import core.render.Renderer2D;

public class GPopup extends GComponent {

  private GComponent component;

  public GPopup(GComponent parent, GComponent component) {
    super(parent, parent.width, parent.height);
    this.component = component;
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(1, 1, 1, 0.1);
   
    Renderer2D.drawFillRect(gl, 0, 0, width, height);
    
    component.render(gl, width, height);
  }

}
