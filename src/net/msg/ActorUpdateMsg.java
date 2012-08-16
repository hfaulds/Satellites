package net.msg;

import geometry.Rotation;
import geometry.Vector2D;

public class ActorUpdateMsg {

  public final Vector2D position;
  public final Rotation rotation;
  public int id;
  
  public ActorUpdateMsg() {
    this(new Vector2D(), new Rotation(), -1);
  }
  
  public ActorUpdateMsg(Vector2D position, Rotation rotation, int id) {
    this.position = position;
    this.rotation = rotation;
    this.id = id;
  }
  
}
