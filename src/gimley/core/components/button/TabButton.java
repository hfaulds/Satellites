package gimley.core.components.button;

import javax.media.opengl.GL2;

import gimley.core.components.GComponent;
import core.geometry.Vector2D;
import core.render.Renderer2D;

public class TabButton extends GButton {

  public TabButton(GComponent parent, Vector2D position, int width, int height,
      String label) {
    super(parent, position, width, height, label);
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawFillRect(gl, parent.position.x + position.x, parent.position.y + position.y, this.width, this.height);

    gl.glColor4d(0.3, 0.3, 0.3, 1.0);
    Renderer2D.drawText(gl, parent.position.x + textPos.x, parent.position.y + textPos.y, label);
  }

}
