package net;

import math.Rotation;
import math.Vector2D;

public class ActorInfo {

  public final Vector2D position;
  public final Rotation rotation;
  
  public ActorInfo(Vector2D position, Rotation rotation) {
    this.position = position;
    this.rotation = rotation;
  }
}
