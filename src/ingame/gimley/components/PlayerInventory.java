package ingame.gimley.components;

import ingame.actors.ShipActor;
import ingame.gimley.icons.ItemIcon;
import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.gimley.components.GTopBar;

public class PlayerInventory extends GComponent {

  private static final int WIDTH = 200;
  private static final int HEIGHT = 200;
  
  private final InventoryPanel inventory;
  
  public PlayerInventory(GComponent parent, ShipActor player) {
    super(parent, WIDTH, HEIGHT);
    inventory = new InventoryPanel(this, new Vector2D(), player.getInventory(), WIDTH, HEIGHT);
    add(inventory);
    add(new GTopBar(this, "Inventory", true, true));
  }

  public void removeItem(ItemIcon icon) {
    inventory.removeItem(icon);
  }

  public void addItem(ItemIcon icon) {
    inventory.addItem(icon);
  }

}