package ingame.items;

import ingame.gimley.icons.ItemIcon;
import core.Item;

public class Ammo001Item implements Item {

  private final ItemIcon icon = new ItemIcon(this);

  @Override
  public ItemIcon getIcon() {
    return icon ;
  }

  @Override
  public String getName() {
    return "Ammo001";
  }

  @Override
  public int getQuantity() {
    return 1;
  }

}
