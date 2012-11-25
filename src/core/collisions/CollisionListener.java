package core.collisions;

import core.Actor;

public abstract class CollisionListener {
  
  private final ClassPair classes;

  public CollisionListener(ClassPair classes) {
    this.classes = classes;
  }
  
  public abstract void collisionStart(Collision collision);
  public abstract void collisionEnd(Collision collision);

  public boolean isListeningFor(Actor a, Actor b) {
    boolean a1 = a.getClass().equals(classes.a);
    boolean b1 = b.getClass().equals(classes.b);
    
    boolean a2 = a.getClass().equals(classes.b);
    boolean b2 = b.getClass().equals(classes.a);
    
    return (a1 && b1) || (a2 && b2);
  }
  
  public Collision correctOrder(Collision collision) {
    if(!collision.a.getClass().equals(classes.a)) {
      Actor a = collision.b;
      Actor b = collision.a;
      collision.a = a;
      collision.b = b;
    }
    return collision;
  }

  protected static class ClassPair {
    public final Class<? extends Actor> a;
    public final Class<? extends Actor> b;

    public ClassPair(Class<? extends Actor> a, Class<? extends Actor> b) {
      this.a = a;
      this.b = b;
    }
  }
}
