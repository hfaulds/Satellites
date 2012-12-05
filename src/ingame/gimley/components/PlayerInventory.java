package ingame.gimley.components;

import ingame.actors.ShipActor;
import ingame.actors.player.PlayerShipActor;

import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.gimley.components.GTopBar;
import core.gimley.listeners.MouseAdapter;

public class PlayerInventory extends GComponent {

  private static final int WIDTH = 400;
  private static final int HEIGHT = 400;
  
  private final InventoryPanel inventory;
  
  public PlayerInventory(GComponent parent, ShipActor player) {
    super(parent, WIDTH, HEIGHT);
    
    inventory = new InventoryPanel(this, new Vector2D(), player.getInventory(), WIDTH, HEIGHT);
    add(inventory);
    
    GTopBar topbar = new GTopBar(this, "Inventory", true, true);
    topbar.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
        inventory.updateIconPositions();
      }
    });
    add(topbar);
  }

  public static PlayerInventory createPlayerInventory(GComponent parent,PlayerShipActor player) {
    PlayerInventory inventory = new PlayerInventory(parent, player);
    inventory.setVisible(false);
    return inventory;
  }

}