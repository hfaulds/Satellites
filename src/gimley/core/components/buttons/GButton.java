package gimley.core.components.buttons;

import gimley.core.ActionListener;
import gimley.core.components.GComponent;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.render.Renderer2D;


public class GButton extends GComponent {

  protected final String label;
  protected Vector2D textPos;
  
  public GButton(GComponent parent, Vector2D position, int width, int height, String label) {
    super(parent, position);
    this.width = width;
    this.height = height;
    this.label = label;
  }
  
  @Override
  public void init(GL2 gl, int width, int height) {
    Vector2D center = position.add(new Vector2D(this.width, this.height).divide(2));
    Vector2D text = Renderer2D.getTextSize(gl, label).divide(2);
    textPos = center.sub(text);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.3, 0.3, 0.3, 1.0);
    Renderer2D.drawFillRect(gl, parent.position.x + position.x, parent.position.y + position.y, this.width, this.height);
    
    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawText(gl, parent.position.x + textPos.x, parent.position.y + textPos.y, label);
  }

  @Override
  public void mouseReleased(Vector2D click, MouseEvent e) {
    super.mouseReleased(click, e);
    for(ActionListener listener : listeners) {
      listener.action();
    }
  }
}
