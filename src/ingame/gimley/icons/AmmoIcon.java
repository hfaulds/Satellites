package ingame.gimley.icons;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;
import core.render.material.Colour;

public class AmmoIcon extends GComponent {

  private final Colour colour = new Colour(1,1,1,1);
  
  public AmmoIcon(String string, int quantity) {
    super(null, new Vector2D());
    this.width = 25;
    this.height = 25;
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4fv(colour.toFloat(), 0);
    Renderer2D.drawRoundedRect(gl, 
        this.parent.position.x + this.position.x, 
        this.parent.position.y + this.position.y,
        this.width, this.height);
  }

  @Override
  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
    this.position._set(end.sub(offset).sub(parent.position));
    this.colour.a = 0.5;
  }

  @Override
  public void mouseReleased(Vector2D click, MouseEvent e) {
    this.colour.a = 1;
  }
  
}