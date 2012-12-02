package ingame.items;

import ingame.gimley.components.icons.ItemIcon;
import core.Item;

public class AmmoItem implements Item {

  private final ItemIcon icon = new ItemIcon(this);

  private final String name;
  private int quantity = 0;
  
  public AmmoItem(String name, int quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  @Override
  public ItemIcon getIcon() {
    return icon;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getQuantity() {
    return quantity;
  }

  public boolean remove(int amount) {
    if(quantity >= amount) {
      quantity -= amount;
      return true;
    } else {
      return false;
    }
  }

}
