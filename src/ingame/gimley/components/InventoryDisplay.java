package ingame.gimley.components;

import java.util.List;

import ingame.actors.ShipActor;

import javax.media.opengl.GL2;

import core.Item;
import core.gimley.components.GComponent;
import core.gimley.components.GTopBar;
import core.render.Renderer2D;

public class InventoryDisplay extends GComponent {
  
  private static final int HEIGHT = 110;
  private static final int WIDTH  = 200;

  private final List<Item> inventory;

  public InventoryDisplay(GComponent parent, ShipActor player) {
    super(parent);
    this.inventory  = player.getInventory();
    this.width = WIDTH;
    this.height = HEIGHT;
    add(new GTopBar(this, "Inventory", true, true));
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

    gl.glPushMatrix();
    gl.glTranslated(position.x, position.y , 0);
    
    for(int i=0; i < inventory.size(); i++) {
      gl.glPushMatrix();
      
      int maxInvX = 5;
      int maxInvY = 5;
      int x = ((i % maxInvX) * 30) + 4;
      int y = this.height - (((i / maxInvY) + 1) * 30) - 2;
      
      gl.glTranslated(x, y, 0);
      inventory.get(i).getIcon().render(gl, width, height);
      gl.glPopMatrix();
    }
    
    gl.glPopMatrix();
    
    super.render(gl, width, height);
  }
}
