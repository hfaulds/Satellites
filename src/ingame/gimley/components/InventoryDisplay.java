package ingame.gimley.components;

import java.util.List;

import ingame.actors.ShipActor;

import javax.media.opengl.GL2;

import core.Item;
import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.gimley.components.GTopBar;
import core.render.Renderer2D;

public class InventoryDisplay extends GComponent {
  
  private static final int HEIGHT = 110;
  private static final int WIDTH  = 200;

  public InventoryDisplay(GComponent parent, ShipActor player) {
    super(parent);
    this.width = WIDTH;
    this.height = HEIGHT;
    add(new GTopBar(this, "Inventory", true, true));

    
    int maxInvX = 5;
    int maxInvY = 5;
    
    List<Item> inventory = player.getInventory();
    for(int i=0; i < inventory.size(); i++) {
      int x = ((i % maxInvX) * 30) + 4;
      int y = this.height - (((i / maxInvY) + 1) * 30) - 2;
      GComponent icon = inventory.get(i).getIcon();
      icon.parent = this;
      icon.position._set(new Vector2D(x, y));
      this.add(icon);
    }
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
