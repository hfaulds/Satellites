package scene.collisions;

import scene.actors.Actor;

public class Collision {

  public Actor a;
  public Actor b;

  public Collision(Actor a, Actor b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Collision other = (Collision) obj;
    if (a == null) {
      if (other.a != null)
        return false;
    } else if (!a.equals(other.a))
      return false;
    if (b == null) {
      if (other.b != null)
        return false;
    } else if (!b.equals(other.b))
      return false;
    return true;
  }
  
}