package core.collisions;

import core.Actor;

public class Collision {
  
  public Actor a;
  public Actor b;

  public Collision(Actor a, Actor b) {
    this.a = a;
    this.b = b;
  }

  public boolean equivalent(Collision other) {
    return (a.equals(other.a) && b.equals(other.b)) || (a.equals(other.b) && b.equals(other.a));
  }

  public Collision flip() {
    return new Collision(b, a);
  }
  
}