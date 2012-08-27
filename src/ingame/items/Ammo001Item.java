package ingame.items;

import ingame.gimley.icons.Icon;
import core.Item;
import core.gimley.components.GComponent;

public class Ammo001Item implements Item {

  private final GComponent icon = new Icon("Ammo001", getQuantity());

  @Override
  public GComponent getIcon() {
    return icon ;
  }

  @Override
  public int getQuantity() {
    return 1;
  }

}
