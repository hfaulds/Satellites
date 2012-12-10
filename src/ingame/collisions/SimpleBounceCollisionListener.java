package ingame.collisions;

import ingame.actors.PlanetActor;
import ingame.actors.StationActor;
import ingame.actors.player.PlayerShipActor;
import core.collisions.ClassPair;
import core.collisions.Collision;
import core.collisions.CollisionListener;

public class SimpleBounceCollisionListener extends CollisionListener {

  public SimpleBounceCollisionListener() {
    super(
        new ClassPair(PlayerShipActor.class, PlanetActor.class), 
        new ClassPair(StationActor.class, PlanetActor.class)
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
