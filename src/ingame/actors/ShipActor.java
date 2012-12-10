package ingame.actors;

import ingame.actors.weapons.Canon001;
import ingame.actors.weapons.WeaponActor;
import ingame.items.AmmoItem;

import java.util.LinkedList;
import java.util.List;

import core.Actor;
import core.ActorInfo;
import core.Inventory;
import core.Item;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ShipActor extends Actor {

  public static final int MAX_HEALTH = 1000;
  public static final int MAX_SHIELD = 1000;

  public int health = MAX_HEALTH;
  public int shield = MAX_SHIELD;

  public static final int MASS  = 1;
  
  public static double WIDTH  = 0.02;
  public static double LENGTH = 1.0;
  public static double HEIGHT = 0.1;

  private final Inventory inventory = new Inventory(new Item[]{
    new AmmoItem("aaasdfasdf", 10),
    new AmmoItem("basdfasdfb", 10),
    new AmmoItem("casdfasdfc", 10),
    new AmmoItem("dasdfasdfd", 10),
    new AmmoItem("esadfasdfe", 10),
    new AmmoItem("ffsdafasdf", 10)
  });

  @SuppressWarnings("serial")
  public final LinkedList<WeaponActor> weapons = new LinkedList<WeaponActor>() {{
    add(new Canon001(position, new Vector2D(0, 0.26142), rotation, inventory.getAmmoItem()));
  }};
  
  
  
  
  
  
  
  
  public ShipActor(Vector2D position, Rotation rotation, double mass, int id, String mesh) {
    super(new ActorInfo(position, rotation, mass, mesh, id));
    addWeapons();
  }

  private void addWeapons() {
    for(WeaponActor weapon : weapons) {
      add(weapon);
    }
  }

  public void damage(int damage) {
    shield -= damage;
    if(shield < 0) {
      health -= shield *-1;
      shield = 0;
    }
  }
  
  public List<Item> getInventory() {
    return inventory.getItems();
  }
}