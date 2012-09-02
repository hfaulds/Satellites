package core.gimley.components.buttons;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;

public class TabButton extends GButton {

  public TabButton(GComponent parent, Vector2D position, int width, int height,
      String label) {
    super(parent, position, width, height, label);
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    Vector2D screenPosition = getScreenPosition();
    
    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawFillRect(gl, screenPosition.x, screenPosition.y, this.width, this.height);

    gl.glColor4d(0.3, 0.3, 0.3, 1.0);
    Renderer2D.drawText(gl, screenPosition.x + textPos.x, screenPosition.y + textPos.y, label);
  }

}
