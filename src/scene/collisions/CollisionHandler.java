package scene.collisions;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class CollisionHandler {
  
  private List<CollisionListener> listeners;
  private HashMap<Collision, Boolean> collisions = new  HashMap<Collision, Boolean>();

  public CollisionHandler(List<CollisionListener> listeners) {
    this.listeners = listeners;
  }
  
  public void tick() {
    for(Entry<Collision, Boolean> entry : collisions.entrySet()) {
      if(!entry.getValue()) {
        removeCollisionEntry(entry.getKey());
      }
    }

    for(Entry<Collision, Boolean> entry : collisions.entrySet()) {
      entry.setValue(false);
    }
  }

  
  private void addCollisionEntry(Collision collision) {
    for(CollisionListener listener : listeners) {
      if(listener.isListeningFor(collision.a, collision.b)) {
        listener.collisionStart(listener.correctOrder(collision));
      }
    }
    collisions.put(collision, true);
  }
  
  private void removeCollisionEntry(Collision collision) {
    for(CollisionListener listener : listeners) {
      if(listener.isListeningFor(collision.a, collision.b)) {
        listener.collisionEnd(listener.correctOrder(collision));
      }
    }
    collisions.remove(collision);
  }
  
  
  private Entry<Collision, Boolean> getExistingEntry(Collision newCollision) {
    for(Entry<Collision, Boolean> entry : collisions.entrySet()) {
      Collision oldCollision = entry.getKey();
      if(oldCollision.equals(newCollision)) {
        return entry;
      }
    }
    return null;
  }

  
  public void addOrUpdateCollision(Collision collision) {
    Entry<Collision, Boolean> entry = getExistingEntry(collision);
    
    if(entry == null) {
      addCollisionEntry(collision);
    } else {
      entry.setValue(true);
    }
    
  }

  
}
