package core.collisions;

import core.Actor;

public class ClassPair {
  public final Class<? extends Actor> a;
  public final Class<? extends Actor> b;

  public ClassPair(Class<? extends Actor> a, Class<? extends Actor> b) {
    this.a = a;
    this.b = b;
  }
}