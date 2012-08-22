package scene.collisions;

import scene.SceneUpdater;
import scene.actors.Actor;
import scene.actors.ProjectileActor;
import scene.actors.ShipActor;

public class ShipProjectileCollisionHandle implements
    CollisionListener {
  
  private final SceneUpdater updater;

  public ShipProjectileCollisionHandle(SceneUpdater sceneUpdater) {
    updater = sceneUpdater;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends Actor>[] getTypes() {
    return new Class[]{ProjectileActor.class, ShipActor.class};
  }

  @Override
  public void collision(Collision collision) {
    ProjectileActor a = (ProjectileActor)collision.a;
    ShipActor b = (ShipActor)collision.b;
    b.damage(ProjectileActor.DAMAGE);
    updater.actorRemoveQueue.add(a);
  }
}