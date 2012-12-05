package ingame.items;

import core.Item;

public class AmmoItem implements Item {

  private final String name;
  private int quantity = 0;
  
  public AmmoItem(String name, int quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getQuantity() {
    return quantity;
  }

  @Override
  public String getQuantityString() {
    return Integer.toString(quantity);
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
