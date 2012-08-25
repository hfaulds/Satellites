package ingame.gimley.icons;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;
import core.render.material.Colour;

public class AmmoIcon extends GComponent {

  Colour colour = new Colour(1,1,1,1);
  
  public AmmoIcon(String string, int quantity) {
    super(null);
    this.width = 25;
    this.height = 25;
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4fv(colour.toFloat(), 0);
    Renderer2D.drawFillRect(gl, 
        this.position.x, this.position.y,
        this.width, this.height);
  }
  
  @Override
  public void mouseDragged(Vector2D click, MouseEvent e) {
    this.position._set(click);
    this.colour.a = 0.5;
  }

  @Override
  public void mouseReleased(Vector2D click, MouseEvent e) {
    this.colour.a = 1;
  }
  
}
