package core;

import ingame.items.AmmoItem;
import ingame.items.NullAmmoItem;

import java.util.LinkedList;
import java.util.List;


public class Inventory {
  
  private final List<AmmoItem> ammo = new LinkedList<AmmoItem>();

  public Inventory(Item[] items) {
    for(Item item : items) {
      if(item instanceof AmmoItem) {
        ammo.add((AmmoItem) item);
      }
    }
  }
  
  public Inventory() {
  }

  public AmmoItem getAmmoItem() {
    return ammo.size() > 0 ? ammo.get(0) : new NullAmmoItem();
  }

  public List<Item> getItems() {
    List<Item> items = new LinkedList<Item>();
    items.addAll(ammo);
    return items;
  }

}
