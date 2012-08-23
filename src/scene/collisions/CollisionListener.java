package scene.collisions;

import scene.Actor;

public abstract class CollisionListener {
  
  private final Class<? extends Actor>[] classes;

  public CollisionListener(Class<? extends Actor>[] classes) {
    this.classes = classes;
  }
  
  public abstract void collisionStart(Collision collision);
  public abstract void collisionEnd(Collision collision);

  public boolean isListeningFor(Actor a, Actor b) {
    boolean a1 = a.getClass().equals(classes[0]);
    boolean b1 = b.getClass().equals(classes[1]);
    
    boolean a2 = a.getClass().equals(classes[1]);
    boolean b2 = b.getClass().equals(classes[0]);
    
    return (a1 && b1) || (a2 && b2);
  }
  
  public Collision correctOrder(Collision collision) {
    if(!collision.a.getClass().equals(classes[0])) {
      Actor a = collision.b;
      Actor b = collision.a;
      collision.a = a;
      collision.b = b;
    }
    return collision;
  }
}
