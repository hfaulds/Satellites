package ingame.items;


public class NullAmmoItem extends AmmoItem {

  public NullAmmoItem() {
    super("", 0);
  }

  @Override
  public String getQuantityString() {
    return "-";
  }

}
