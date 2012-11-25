package ingame.collisions;

import ingame.actors.Planet001Actor;
import ingame.actors.StationActor;
import core.collisions.Collision;
import core.collisions.CollisionListener;

public class PlanetStationCollisionListener extends CollisionListener {

  public PlanetStationCollisionListener() {
    super(new ClassPair(StationActor.class, Planet001Actor.class));
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
