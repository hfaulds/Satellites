package ingame.items;

import ingame.gimley.components.icons.ItemIcon;
import core.Item;

public class AmmoItem implements Item {

  private final ItemIcon icon = new ItemIcon(this);

  private final String name;
  
  public AmmoItem(String name) {
    this.name = name;
  }

  @Override
  public ItemIcon getIcon() {
    return icon ;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getQuantity() {
    return 1;
  }

}
