package ingame.items;

import ingame.gimley.icons.Icon;
import core.Item;
import core.gimley.components.GComponent;

public class Ammo002Item implements Item {
  
  private final GComponent icon = new Icon("Ammo002", getQuantity());

  @Override
  public GComponent getIcon() {
    return icon;
  }

  @Override
  public int getQuantity() {
    return 5;
  }

}
