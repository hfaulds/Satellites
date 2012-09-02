package ingame.gimley.icons;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.Item;
import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;
import core.render.material.Colour;

public class ItemIcon extends GComponent {

  public static final int SIZE = 75;

  private final Colour colour = new Colour(1,1,1,1);

  public final Item item;
  private final String name;
  private final int quantity;

  public ItemIcon(Item item) {
    super(null, new Vector2D(), SIZE, SIZE);
    this.item = item;
    this.name = item.getName();
    this.quantity = item.getQuantity();
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4fv(colour.toFloat(), 0);
    
    Vector2D position = this.getScreenPosition();
    Renderer2D.drawFillRect(gl, 
        position.x, 
        position.y, 
        this.width, 
        this.height, 
        5);
    
    gl.glColor4d(0.5,0.5,0.5,1);
    Renderer2D.drawLineRect(gl, 
        position.x, 
        position.y, 
        this.width, 
        this.height, 
        1, 
        5);
    
    double nameHeight = Renderer2D.getTextSize(gl, name).y;
    Renderer2D.drawText(gl,
        this.getScreenPosition().x + 5,
        this.getScreenPosition().y + this.height - nameHeight - 5,
        name);

    double quantityWidth = Renderer2D.getTextSize(gl, Integer.toString(quantity)).x;
    
    Renderer2D.drawText(gl, 
        this.getScreenPosition().x + this.width - quantityWidth - 5,
        this.getScreenPosition().y + 5,
        Integer.toString(quantity));
  }

  @Override
  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
    this.position._set(end.sub(offset).sub(parent.position));
    this.colour.a = 0.5;
    super.mouseDragged(start, end, offset, e);
  }

  @Override
  public void mouseReleased(Vector2D click, MouseEvent e) {
    this.colour.a = 1.0;
    super.mouseReleased(click, e);
  }
  
}