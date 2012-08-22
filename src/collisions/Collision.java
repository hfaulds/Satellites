package collisions;

import scene.actors.Actor;

public class Collision {

  public final Actor a;
  public final Actor b;

  public Collision(Actor a, Actor b, Class<? extends Actor>[] classes) {
    if(a.getClass().equals(classes[0])) {
      this.a = a;
      this.b = b;
    } else {
      this.a = b;
      this.b = a;
    }
  }
  
}