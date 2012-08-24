package ingame.collisions;

import ingame.actors.ProjectileActor;
import ingame.actors.ship.ShipActor;
import core.SceneUpdater;

public class ShipProjectileCollisionHandle extends
    CollisionListener {
  
  private final SceneUpdater updater;

  @SuppressWarnings("unchecked")
  public ShipProjectileCollisionHandle(SceneUpdater updater) {
    super(new Class[]{ProjectileActor.class, ShipActor.class});
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