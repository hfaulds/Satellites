package core.gimley.actions;

import core.geometry.Vector2D;
import ingame.gimley.icons.ItemIcon;

public class ItemMoved extends Action {

  public final ItemIcon icon;
  public final Vector2D location;

  public ItemMoved(ItemIcon icon, Vector2D location) {
    this.icon = icon;
    this.location = location;
  }

}
