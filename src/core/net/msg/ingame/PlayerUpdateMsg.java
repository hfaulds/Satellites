package core.net.msg.ingame;

import core.geometry.Rotation;
import core.geometry.Vector2D;

public class PlayerUpdateMsg {

  public final Vector2D velocity;
  public final Rotation spin;

  public PlayerUpdateMsg() {
    this(new Vector2D(), new Rotation());
  }
  
  public PlayerUpdateMsg(Vector2D velocity, Rotation spin) {
    this.velocity = velocity;
    this.spin = spin;
  }
  
}
