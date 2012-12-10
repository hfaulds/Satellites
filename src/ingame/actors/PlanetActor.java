package ingame.actors;

import core.Actor;
import core.ActorInfo;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class PlanetActor extends Actor {

  public PlanetActor(Vector2D position, Rotation rotation, double mass, int id, String mesh) {
    super(new ActorInfo(position, rotation, mass, mesh, id));
  }
  
}
