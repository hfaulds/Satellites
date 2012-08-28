package ingame.gimley.icons;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;
import core.render.material.Colour;

public class ItemIcon extends GComponent {

  public static final int SIZE = 75;

  private final Colour colour = new Colour(1,1,1,1);
  
  public final String name;
  private final int quantity;
  
  public ItemIcon(String name, int quantity) {
    super(null, new Vector2D(), SIZE, SIZE);
    this.name = name;
    this.quantity = quantity;
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4fv(colour.toFloat(), 0);
    Renderer2D.drawFillRect(gl, 
        this.parent.position.x + this.position.x,
        this.parent.position.y + this.position.y,
        this.width, this.height,
        5);
    
    gl.glColor4d(0.5,0.5,0.5,1);
    Renderer2D.drawLineRect(gl,
        this.parent.position.x + this.position.x,
        this.parent.position.y + this.position.y,
        this.width, this.height,
        1f, 
        5);
    
    double nameHeight = Renderer2D.getTextSize(gl, name).y;
    Renderer2D.drawText(gl, 
        this.parent.position.x + this.position.x + 5,
        this.parent.position.y + this.position.y + this.height - nameHeight - 5,
        name);

    String quantityString = Integer.toString(quantity);
    double quantityWidth = Renderer2D.getTextSize(gl, quantityString).x;
    Renderer2D.drawText(gl, 
        this.parent.position.x + this.position.x + this.width - quantityWidth - 5,
        this.parent.position.y + this.position.y + 5,
        quantityString);
  }

  @Override
  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
    this.position._set(end.sub(offset).sub(parent.position));
    this.colour.a = 0.5;
  }

  @Override
  public void mouseReleased(Vector2D click, MouseEvent e) {
    this.colour.a = 1;
    super.mouseReleased(click, e);
  }
  
}