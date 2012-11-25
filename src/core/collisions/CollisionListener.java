package core.collisions;

import core.Actor;

public abstract class CollisionListener {
  
  private final ClassPair[] pairs;

  public CollisionListener(ClassPair ... pairs) {
    this.pairs = pairs;
  }
  
  public abstract void collisionStart(Collision collision);
  public abstract void collisionEnd(Collision collision);

  public ClassPair getClassPairMatching(Actor a, Actor b) {
    for(ClassPair pair : pairs) {
      boolean a1 = a.getClass().equals(pair.a);
      boolean b1 = b.getClass().equals(pair.b);
      
      boolean a2 = a.getClass().equals(pair.b);
      boolean b2 = b.getClass().equals(pair.a);
    
      if((a1 && b1) || (a2 && b2)) {
        return pair;
      }
    }
    return null;
  }

  public void collisionStart(Collision collision, ClassPair pair) {
    if(collision.a.getClass().equals(pair.a)) {
      collisionStart(collision);
    } else {
      collisionStart(collision.flip());
    }
  }

  public void collisionEnd(Collision collision, ClassPair pair) {
    if(collision.a.getClass().equals(pair.a)) {
      collisionEnd(collision);
    } else {
      collisionEnd(collision.flip());
    }
  }
}
