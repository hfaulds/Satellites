package ingame.gimley.components;

import java.util.List;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import ingame.gimley.icons.ItemIcon;
import core.Item;
import core.geometry.Vector2D;
import core.gimley.actions.ItemMoved;
import core.gimley.components.GComponent;
import core.gimley.listeners.ActionListener;
import core.gimley.listeners.MouseAdapter;
import core.render.Renderer2D;

public class InventoryPanel extends GComponent {

  private final List<Item> inventory;
  
  public InventoryPanel(GComponent parent, Vector2D position, List<Item> inventory, int width, int height) {
    super(parent, parent.position, width, height);
    this.inventory = inventory;
    
    for(Item item : inventory) {
      final ItemIcon icon = item.getIcon();
      add(icon);
      icon.parent = this;
      icon.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(Vector2D click, MouseEvent e) {
          if(testClickNonRecursive(click)) {
            updateIconPositions();
          } else {
            removeItem(icon);
            for(ActionListener listener : actionListeners) {
              listener.action(new ItemMoved(icon, click));
            }
          }
        }
       
      });
      
    }
    updateIconPositions();
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
  
  public void removeItem(ItemIcon icon) {
    super.remove(icon);
    inventory.remove(icon.item);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, 
        position.x, position.y, 
        this.width, this.height);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, 
        position.x, position.y, 
        this.width, this.height, 0.9f);
    
    super.render(gl, width, height);
  }
}
