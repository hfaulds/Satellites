package core;

import ingame.gimley.components.icons.ItemIcon;

public interface Item {
  public ItemIcon getIcon();
  public String getName();
  
  public int getQuantity();
  public String getQuantityString();
}
