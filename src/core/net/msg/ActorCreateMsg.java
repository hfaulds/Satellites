package core.net.msg;


import java.util.ArrayList;
import java.util.List;

import core.Actor;
import core.Scene;
import core.geometry.Rotation;
import core.geometry.Vector2D;


public class ActorCreateMsg {

  public final Vector2D position;
  public final Rotation rotation;
  public final int id;
  public final double mass;
  public final Class<? extends Actor> actorClass;
  
  public ActorCreateMsg() {
    this(new Vector2D(), new Rotation(), 0, 0, Actor.class);
  }
  
  public ActorCreateMsg(Vector2D position, Rotation rotation, int id, double mass, Class<? extends Actor> actorClass) {
    this.position = position;
    this.rotation = rotation;
    this.id = id;
    this.mass = mass;
    this.actorClass = actorClass;
  }
  
  public static List<ActorCreateMsg> actorInfoList(Scene scene) {
    List<ActorCreateMsg> infoList = new ArrayList<ActorCreateMsg>();
    
    for(Actor actor: scene.actors)
      infoList.add(actor.getCreateMsg());

    for(Actor actor: scene.actorqueue)
      infoList.add(actor.getCreateMsg());
    
    return infoList;
  }
  
  @Override
  public String toString() {
    return "id " + id + "  actorClass " + actorClass.toString() + "\n";
  }
}
