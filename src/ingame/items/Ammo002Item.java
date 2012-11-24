package ingame.items;

import ingame.gimley.components.ItemIcon;
import core.Item;

public class Ammo002Item implements Item {
  
  private final ItemIcon icon = new ItemIcon(this);

  @Override
  public ItemIcon getIcon() {
    return icon;
  }

  @Override
  public String getName() {
    return "Ammo002";
  }

  @Override
  public int getQuantity() {
    return 5;
  }

}
