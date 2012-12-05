package core.gimley.actions;

import ingame.gimley.icons.InventoryItemIcon;
import core.geometry.Vector2D;

public class ItemMoved extends ActionEvent {

  public final InventoryItemIcon icon;
  public final Vector2D location;

  public ItemMoved(InventoryItemIcon icon, Vector2D location) {
    this.icon = icon;
    this.location = location;
  }

}
