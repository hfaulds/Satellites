package core.collisions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class CollisionHandler {
  
  private List<CollisionListener> listeners;
  private HashMap<Collision, Boolean> collisions = new HashMap<Collision, Boolean>();

  public CollisionHandler(List<CollisionListener> listeners) {
    this.listeners = listeners;
  }
  
  public void tick() {
    synchronized(collisions) {
      Iterator<Entry<Collision, Boolean>> iterator = collisions.entrySet().iterator();
      
      while (iterator.hasNext()) {
          Entry<Collision, Boolean> entry = iterator.next();
          if(!entry.getValue()) {
            Collision collision = entry.getKey();
            
            for(CollisionListener listener : listeners) {
              ClassPair pair = listener.getClassPairMatching(collision.a, collision.b);
              if(pair != null) {
                listener.collisionEnd(collision, pair);
              }
            }
            
            iterator.remove();
          }
      }
  
      for(Entry<Collision, Boolean> entry : collisions.entrySet()) {
        entry.setValue(false);
      }
    }
  }

  
  private void addCollisionEntry(Collision collision) {
    for(CollisionListener listener : listeners) {
      ClassPair pair = listener.getClassPairMatching(collision.a, collision.b);
      if(pair != null) {
        listener.collisionStart(collision, pair);
      }
    }
    collisions.put(collision, true);
  }
  
  private Entry<Collision, Boolean> getExistingEntry(Collision newCollision) {
    for(Entry<Collision, Boolean> entry : collisions.entrySet()) {
      Collision oldCollision = entry.getKey();
      if(oldCollision.equivalent(newCollision)) {
        return entry;
      }
    }
    return null;
  }

  
  public void addOrUpdateCollision(Collision collision) {
    synchronized(collisions) {
      Entry<Collision, Boolean> entry = getExistingEntry(collision);
      
      if(entry == null) {
        addCollisionEntry(collision);
      } else {
        entry.setValue(true);
      }
    }
  }

  
}
