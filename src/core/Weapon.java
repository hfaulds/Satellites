package core;

import ingame.gimley.WeaponIcon;
import ingame.gimley.components.icons.ItemIcon;

public interface Weapon {
  public WeaponIcon getIcon();
  public String getName();
  public Actor getActor();
}
