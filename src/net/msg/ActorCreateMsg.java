package net.msg;

import geometry.Rotation;
import geometry.Vector2D;

import java.util.ArrayList;
import java.util.List;

import scene.Scene;
import scene.actors.Actor;

public class ActorCreateMsg {

  public final Vector2D position;
  public final Rotation rotation;
  public final double mass;
  public final int id;
  public final Class<? extends Actor> actorClass;
  
  public ActorCreateMsg() {
    this(new Vector2D(), new Rotation(), 0, 0, Actor.class);
  }
  
  public ActorCreateMsg(Vector2D position, Rotation rotation, int id, double mass, Class<? extends Actor> actorClass) {
    this.position = position;
    this.rotation = rotation;
    this.mass = mass;
    this.id = id;
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
}
