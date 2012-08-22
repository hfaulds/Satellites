package scene;

import scene.actors.Actor;

public interface CollisionListener {
  public Class<? extends Actor>[] getTypes();
  public void collision(Collision collision);
}
