package core;

import ingame.gimley.components.ItemIcon;

public interface Item {
  public ItemIcon getIcon();
  public String getName();
  public int getQuantity();
}
