package ingame.gimley.components;

import ingame.actors.ShipActor;
import ingame.gimley.icons.ItemIcon;

import java.util.List;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.Item;
import core.geometry.Vector2D;
import core.gimley.actions.IconMoved;
import core.gimley.components.GComponent;
import core.gimley.components.GTopBar;
import core.gimley.listeners.ActionListener;
import core.gimley.listeners.MouseAdapter;
import core.render.Renderer2D;

public class InventoryDisplay extends GComponent {

  private static final int MAX_ITEMS_X = 5;
  private static final int MAX_ITEMS_Y = 5;
  
  private static final int WIDTH = MAX_ITEMS_X * ItemIcon.SIZE;
  private static final int HEIGHT = MAX_ITEMS_Y * ItemIcon.SIZE;
  
  private List<Item> inventory;

  public InventoryDisplay(GComponent parent, ShipActor player) {
    super(parent, WIDTH, HEIGHT);
    
    add(new GTopBar(this, "Inventory", true, true));

    this.inventory = player.getInventory();
    
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
              listener.action(new IconMoved(icon));
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
    for(int i=0; i < inventory.size(); i++) {
      final ItemIcon icon = inventory.get(i).getIcon();
      final int x = ((i % MAX_ITEMS_X) * icon.width) + 4;
      final int y = this.height - (((i / MAX_ITEMS_Y) + 1) * icon.height) - 2;
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
        this.width, HEIGHT);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, 
        position.x, position.y, 
        this.width, HEIGHT, 0.9f);
    
    super.render(gl, width, height);
  }
}
