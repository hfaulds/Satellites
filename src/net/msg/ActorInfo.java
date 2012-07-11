package net.msg;

import java.util.ArrayList;
import java.util.List;

import math.Rotation;
import math.Vector2D;
import actors.Actor;

public class ActorInfo {

  public final Vector2D position;
  public final Rotation rotation;
  public final double mass;
  public final int id;
  public final Class<Actor> actorClass;
  
  public ActorInfo() {
    this(new Vector2D(), new Rotation(), 0, 0, Actor.class);
  }
  
  public ActorInfo(Vector2D position, Rotation rotation, int id, double mass, Class<Actor> actorClass) {
    this.position = position;
    this.rotation = rotation;
    this.mass = mass;
    this.id = id;
    this.actorClass = actorClass;
  }
  
  public static List<ActorInfo> actorInfoList(List<Actor> actors) {
    List<ActorInfo> infoList = new ArrayList<ActorInfo>();
    for(Actor actor: actors)
      infoList.add(actor.getInfo());
    return infoList;
  }
}
