package net;

import math.Rotation;
import math.Vector2D;

public class PlayerInfo {

  public final Vector2D velocity;
  public final Rotation spin;

  public PlayerInfo(Vector2D velocity, Rotation spin) {
    this.velocity = velocity; 
    this.spin = spin;
  }
}
