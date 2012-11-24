package core.gimley.actions;

import ingame.gimley.components.ItemIcon;
import core.geometry.Vector2D;

public class ItemMoved extends ActionEvent {

  public final ItemIcon icon;
  public final Vector2D location;

  public ItemMoved(ItemIcon icon, Vector2D location) {
    this.icon = icon;
    this.location = location;
  }

}
