package ingame.gimley.components;

import ingame.gimley.icons.ItemIcon;

import java.util.LinkedList;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.Item;
import core.geometry.Vector2D;
import core.gimley.actions.ItemMoved;
import core.gimley.components.GComponent;
import core.gimley.listeners.ActionListener;
import core.gimley.listeners.MouseAdapter;
import core.render.Renderer2D;

public class InventoryPanel extends GComponent {

  private final class IconMouseListener extends MouseAdapter {
    private final ItemIcon icon;
    boolean dragging = false;

    private IconMouseListener(ItemIcon icon) {
      this.icon = icon;
    }

    @Override
    public void mousePressed(Vector2D mouse, MouseEvent e) {
      dragging = true;
    }

    @Override
    public void mouseReleased(Vector2D mouse, MouseEvent e) {
      if(dragging) {
        if(testClick(new Vector2D(e.getX(), e.getY()))) {
          int itemIndex = getIconIndexAt(icon.position);
          if(itemIndex != -1) {
            moveItem(icon, itemIndex);
          }
          updateIconPositions();
        } else {
          removeItem(icon);
          for(ActionListener listener : actionListeners) {
            listener.action(new ItemMoved(icon, new Vector2D(e.getX(), e.getY())));
          }
        }
      }
      dragging = false;
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////

  private final LinkedList<Item> inventory;
  
  public InventoryPanel(GComponent parent, Vector2D position, LinkedList<Item> inventory, int width, int height) {
    super(parent, new Vector2D(), width, height);
    this.inventory = inventory;
    
    for(Item item : inventory) {
      final ItemIcon icon = item.getIcon();
      add(icon);
      icon.parent = this;
      icon.addMouseListener(new IconMouseListener(icon));
      
    }
      
    updateIconPositions();
  }
  
  protected void moveItem(ItemIcon icon, int newIndex) {
    int currentIndex = inventory.indexOf(icon.item);

    if(newIndex != currentIndex) {
      inventory.remove(icon.item);
      
      if(newIndex > inventory.size()) {
        inventory.add(icon.item);
      } else {
        inventory.add(newIndex, icon.item);
      }
    }
  }

  public void addItem(ItemIcon icon) {
    super.add(icon);
    inventory.add(icon.item);
    updateIconPositions();
  }
  
  private void updateIconPositions() {
    int maxItemsX = this.width / ItemIcon.SIZE;
    
    for(int i=0; i < inventory.size(); i++) {
      ItemIcon icon = inventory.get(i).getIcon();
      
      int x = ((i % maxItemsX) * icon.width) + 4;
      int y = this.height - (((i / maxItemsX) + 1) * icon.height) - 2;
      
      if(y < position.y)
        break;
      
      icon.position._set(new Vector2D(x, y));
    }
  }
  
  private int getIconIndexAt(Vector2D position) {
    if(position.x > width || position.y > height)
      return -1;

    int xIndex = (int)(position.x / ItemIcon.SIZE);
    int yIndex = (int)(position.y / ItemIcon.SIZE) + 1;
    
    int maxItemsX = this.width / ItemIcon.SIZE;
    int maxItemsY = this.height / ItemIcon.SIZE;
    
    return xIndex + (maxItemsY - yIndex) * maxItemsX;
  }
  
  public void removeItem(ItemIcon icon) {
    super.remove(icon);
    inventory.remove(icon.item);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    Vector2D screenPosition = getScreenPosition();
    
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, 
        screenPosition.x, screenPosition.y, 
        this.width, this.height);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, 
        screenPosition.x, screenPosition.y, 
        this.width, this.height, 0.9f);
    
    super.render(gl, width, height);
  }
}
