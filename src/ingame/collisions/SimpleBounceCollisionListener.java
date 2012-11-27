package ingame.collisions;

import ingame.actors.Planet001Actor;
import ingame.actors.PlayerShipActor;
import ingame.actors.StationActor;
import core.collisions.ClassPair;
import core.collisions.Collision;
import core.collisions.CollisionListener;

public class SimpleBounceCollisionListener extends CollisionListener {

  public SimpleBounceCollisionListener() {
    super(
        new ClassPair(PlayerShipActor.class, Planet001Actor.class), 
        new ClassPair(StationActor.class, Planet001Actor.class)
        );
  }

  @Override
  public void collisionStart(Collision collision) {
    collision.a.velocity._multiply(-1);
    collision.b.velocity._multiply(-1);
  }

  @Override
  public void collisionEnd(Collision collision) {
    
  }

}
