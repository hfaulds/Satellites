package ingame.collisions;

import ingame.actors.ProjectileActor;
import ingame.actors.ShipActor;
import ingame.actors.player.PlayerShipActor;
import core.SceneUpdater;
import core.collisions.ClassPair;
import core.collisions.Collision;
import core.collisions.CollisionListener;

public class ShipProjectileCollisionListener extends CollisionListener {
  
  private final SceneUpdater updater;

  public ShipProjectileCollisionListener(SceneUpdater updater) {
    super(new ClassPair(ProjectileActor.class, ShipActor.class), new ClassPair(ProjectileActor.class, PlayerShipActor.class));
    this.updater = updater;
  }
 
  @Override
  public void collisionStart(Collision collision) {
    ProjectileActor a = (ProjectileActor)collision.a;
    ShipActor b = (ShipActor)collision.b;
    b.damage(ProjectileActor.DAMAGE);
    updater.actorRemoveQueue.add(a);
  }

  @Override
  public void collisionEnd(Collision collision) {
    
  }
}