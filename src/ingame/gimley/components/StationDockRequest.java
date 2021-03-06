package ingame.gimley.components;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.gimley.components.buttons.GButton;
import core.render.Renderer2D;

public class StationDockRequest extends GComponent {
  
  private static final int HEIGHT = 35;
  private static final int WIDTH = 275;
  
  public final GButton dock = new GButton(this, new Vector2D(200, 5), 65, 25, "DOCK");
  
  public StationDockRequest(GComponent parent) {
    super(parent, new Vector2D(0, 725), WIDTH, HEIGHT);
    add(dock);
    setVisible(false);
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.5, 0.5, 0.5, 0.9);
    Renderer2D.drawFillRect(gl, position.x, position.y, this.width, this.height);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawText(gl, position.x + 10, position.y + 10, GLUT.BITMAP_HELVETICA_18, "DOCKING REQUEST");

    super.render(gl, width, height);
  }

}