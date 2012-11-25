package ingame.collisions;

import ingame.actors.Planet001Actor;
import ingame.actors.PlayerShipActor;
import core.collisions.Collision;
import core.collisions.CollisionListener;

public class ShipPlanetCollisionListener extends CollisionListener {

  public ShipPlanetCollisionListener() {
    super(new ClassPair(PlayerShipActor.class, Planet001Actor.class));
  }

  @Override
  public void collisionStart(Collision collision) {
    collision.a.velocity._mult(-1);
    collision.b.velocity._mult(-1);
  }

  @Override
  public void collisionEnd(Collision collision) {
    // TODO Auto-generated method stub
    
  }

}
