package core.net.msg.ingame;


import java.util.ArrayList;
import java.util.List;

import core.Actor;
import core.Scene;
import core.geometry.Rotation;
import core.geometry.Vector2D;


public class ActorCreateMsg {

  public final int id;
  public final Class<? extends Actor> actorClass;
  
  public final Vector2D position;
  public final Rotation rotation;
  
  public final double mass;
  public final String mesh;
  
  public ActorCreateMsg() {
    this(new Vector2D(), new Rotation(), 0, 0, Actor.class, "");
  }
  
  public ActorCreateMsg(Vector2D position, Rotation rotation, int id, double mass, Class<? extends Actor> actorClass, String mesh) {
    this.position = position;
    this.rotation = rotation;
    this.id = id;
    this.mass = mass;
    this.actorClass = actorClass;
    this.mesh = mesh;
  }
  
  public static List<ActorCreateMsg> actorInfoList(Scene scene) {
    List<ActorCreateMsg> infoList = new ArrayList<ActorCreateMsg>();
    
    synchronized(scene.actors) {
      for(Actor actor: scene.actors)
        infoList.add(actor.getCreateMsg());
    }

    synchronized(scene.actorqueue) {
      for(Actor actor: scene.actorqueue)
        infoList.add(actor.getCreateMsg());
    }
    
    return infoList;
  }
  
  @Override
  public String toString() {
    return "id " + id + "  actorClass " + actorClass.toString() + "\n";
  }
}
