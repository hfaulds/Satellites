package ingame.actors;

import ingame.items.AmmoItem;

import java.util.LinkedList;

import core.Actor;
import core.ActorInfo;
import core.Item;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class ShipActor extends Actor {

  public static final int MAX_HEALTH = 1000;
  public static final int MAX_SHIELD = 1000;

  public static final Mesh MESH = MeshLoader.loadMesh("Ship-Mk2.obj");
  
  private static double MASS   = 0.001;
  
  public static double WIDTH   = 0.02;
  public static double LENGTH  = 1.0;
  public static double HEIGHT  = 0.1;

  public int health = MAX_HEALTH;
  public int shield = MAX_SHIELD;
  
  @SuppressWarnings("serial")
  private final LinkedList<Item> inventory = new LinkedList<Item>() {{
    add(new AmmoItem("aaasdfasdf"));
    add(new AmmoItem("basdfasdfb"));
    add(new AmmoItem("casdfasdfc"));
    add(new AmmoItem("dasdfasdfd"));
    add(new AmmoItem("esadfasdfe"));
    add(new AmmoItem("ffsdafasdf"));
  }};

  public ShipActor(Vector2D position, Rotation rotation, double mass, int id) {
    super(new ActorInfo(position, rotation, mass, MESH, id));
  }
  
  public ShipActor(double x, double y) {
    super(new ActorInfo(new Vector2D(x,y), new Rotation(), MASS, MESH));
  }

  public void damage(int damage) {
    shield -= damage;
    if(shield < 0) {
      health -= shield *-1;
      shield = 0;
    }
  }
  
  public LinkedList<Item> getInventory() {
    return inventory;
  }
}