package ingame.gimley.components;


import java.util.LinkedList;

import javax.media.opengl.GL2;

import core.Item;
import core.geometry.Vector2D;
import core.gimley.actions.ActionEvent;
import core.gimley.actions.ItemMoved;
import core.gimley.components.GComponent;
import core.gimley.listeners.ActionListener;
import core.render.Renderer2D;

public class InventoryPanel extends GComponent {
  
  private final LinkedList<Item> inventory;
  
  public InventoryPanel(GComponent parent, Vector2D position, LinkedList<Item> inventory, int width, int height) {
    super(parent, new Vector2D(), width, height);
    this.inventory = inventory;
    
    for(Item item : inventory) {
      final ItemIcon icon = item.getIcon();
      add(icon);
      icon.addActionListener(new ActionListener() {

        @Override
        public void action(ActionEvent obj) {
          ItemMoved event = (ItemMoved) obj;
          int index = getIconIndexAt(event.location);
          if(index > 0) {
            moveItem(icon, index);
          }
          updateIconPositions();
        }
        
      });
      
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
  
  public void updateIconPositions() {
    int maxItemsX = this.width / ItemIcon.SIZE;
    
    for(int i=0; i < inventory.size(); i++) {
      ItemIcon icon = inventory.get(i).getIcon();
      
      int x = ((i % maxItemsX) * icon.width) + 4;
      int y = this.height - (((i / maxItemsX) + 1) * icon.height) - 2;
      
      if(y < position.y)
        break;
      
      icon.position._set(this.getScreenPosition().add(new Vector2D(x, y)));
    }
  }
  
  private int getIconIndexAt(Vector2D position) {
    Vector2D localPosition = position.sub(this.getScreenPosition());
    localPosition.y = this.height - position.y;

    if(position.x > width || position.y > height)
      return -1;
    
    int xIndex = (int)(localPosition.x / ItemIcon.SIZE);
    int yIndex = (int)(localPosition.y / ItemIcon.SIZE);

    int maxItemsX = this.width / ItemIcon.SIZE;
    return xIndex + (yIndex * maxItemsX);
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
