package net.msg;

import math.Rotation;
import math.Vector2D;

public class PlayerMsg {

  public final Vector2D velocity;
  public final Rotation spin;

  public PlayerMsg() {
    this(new Vector2D(), new Rotation());
  }
  
  public PlayerMsg(Vector2D velocity, Rotation spin) {
    this.velocity = velocity; 
    this.spin = spin;
  }
}
