package ingame.gimley.icons;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.Item;
import core.geometry.Vector2D;
import core.gimley.actions.ItemMoved;
import core.gimley.components.GComponent;
import core.gimley.listeners.ActionListener;
import core.render.Renderer2D;
import core.render.material.Colour;

public class ItemIcon extends GComponent {

  public static final int SIZE = 75;

  private final Colour fillColour = new Colour(1,1,1,1);
  private final Colour outlineColour = new Colour(0.5,0.5,0.5,1);
  private final Colour textColour = new Colour(1,1,1,1);
  public final Item item;

  public ItemIcon(Item item) {
    super(null, new Vector2D(), SIZE, SIZE);
    this.item = item;
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    Vector2D screenPosition = this.getScreenPosition();

    gl.glColor4fv(fillColour.toFloat(), 0);
    Renderer2D.drawFillRect(gl, 
        screenPosition.x, 
        screenPosition.y, 
        this.width,
        this.height, 
        5);

    gl.glColor4fv(outlineColour.toFloat(), 0);
    Renderer2D.drawLineRect(gl, 
        screenPosition.x, 
        screenPosition.y, 
        this.width, 
        this.height, 
        1, 
        5);

    gl.glColor4fv(textColour.toFloat(), 0);
    double nameHeight = Renderer2D.getTextSize(gl, item.getName()).y;
    Renderer2D.drawText(gl,
        screenPosition.x + 5,
        screenPosition.y + this.height - nameHeight - 5,
        item.getName());


    String ammoQuantity = item.getQuantityString();
    double quantityWidth = Renderer2D.getTextSize(gl, ammoQuantity).x;
    Renderer2D.drawText(gl, 
        screenPosition.x + this.width - quantityWidth - 5,
        screenPosition.y + 5,
        Integer.toString(item.getQuantity()));
  }

  @Override
  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
    this.position._set(end.sub(offset));
  }

  @Override
  public void mouseReleased(Vector2D mouse, MouseEvent e) {
    for(ActionListener listener : actionListeners) {
      listener.action(new ItemMoved(this, mouse));
    }
  }
  
}