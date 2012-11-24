package core.gimley;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;

public class GComponentList implements List<GComponent> {

  private final ArrayList<GComponent> initComponents = new ArrayList<GComponent>();
  private final ArrayList<GComponent> unInitComponents = new ArrayList<GComponent>();
  
  private static final double MAX_COMPONENTS = 15;
  
  private final GComponent parent;
  private GComponent focus;
  
  public GComponentList(GComponent parent, GComponent ... components) {
    unInitComponents.addAll(Arrays.asList(components));
    
    this.focus = this.parent = parent;
  }
  
  public GComponent getFocus() {
    return focus;
  }
  
  public GComponent getComponentAt(Vector2D click) {
    synchronized(this) {
      GComponent focus = getFocus();
      
      if(!focus.testClick(click) || focus == parent) {
        List<GComponent> componentsHit = new LinkedList<GComponent>();
        
        for(GComponent component : initComponents) {
          if(component.testClick(click)) {
            componentsHit.add(component);
          }
        }
        
        if(componentsHit.size() > 0) {
          return componentsHit.get(0);
        } else {
          return parent;
        }
      }
      
      return focus;
    }
  }
  
  public GComponent setFocus(GComponent focus) {
    if(focus.getVisible()) {
      synchronized(this) {
        if(initComponents.contains(focus)) {
          initComponents.remove(focus);
          initComponents.add(focus);
        }
      }
      
      this.focus = focus;
    } else {
      setFocus(focus.parent);
    }
    return focus;
  }

  private void initComponents(GL2 gl, int width, int height) {
    for(GComponent component : unInitComponents) {
      component.init(gl, width, height);
    }
    
    synchronized(this) {
      initComponents.addAll(unInitComponents);
      unInitComponents.clear();
    }
  }
  
  private void renderComponents(GL2 gl, int width, int height) {
    for(int z=0; z < initComponents.size(); z++) {
      GComponent component = initComponents.get(z);
      if(component.getVisible()) {
        gl.glPushMatrix();
        gl.glTranslated(0, 0, (double)-z/MAX_COMPONENTS);
        
        component.render(gl, width, height);
        
        gl.glPopMatrix();
      }
    }
  }

  public void init(GL2 gl, int width, int height) {
    initComponents(gl, width, height);
  }
  
  public void render(GL2 gl, int width, int height) {
    initComponents(gl, width, height);
    renderComponents(gl, width, height);
  }

  
  /* List functions */
  
  public boolean add(GComponent component) {
    synchronized(this) {
      return unInitComponents.add(component);
    }
  }
  
  @Override
  public void add(int index, GComponent component) {
    synchronized(this) {
      unInitComponents.add(index, component);
    }
  }

  @Override
  public boolean addAll(Collection<? extends GComponent> components) {
    synchronized(this) {
      return unInitComponents.addAll(components);
    }
  }

  @Override
  public boolean addAll(int index, Collection<? extends GComponent> components) {
    synchronized(this) {
      return unInitComponents.addAll(index, components);
    }
  }

  @Override
  public void clear() {
    synchronized(this) {
      unInitComponents.clear();
      initComponents.clear();
    }
  }

  @Override
  public boolean contains(Object component) {
    return unInitComponents.contains(component) || initComponents.contains(component);
  }

  @Override
  public boolean containsAll(Collection<?> components) {
    return unInitComponents.containsAll(components) || initComponents.containsAll(components);
  }

  @Override
  public GComponent get(int component) {
    return initComponents.get(component);
  }

  @Override
  public int indexOf(Object component) {
    return initComponents.indexOf(component);
  }

  @Override
  public boolean isEmpty() {
    return unInitComponents.isEmpty() && initComponents.isEmpty();
  }

  @Override
  public Iterator<GComponent> iterator() {
    return initComponents.iterator();
  }

  @Override
  public int lastIndexOf(Object component) {
    return initComponents.lastIndexOf(component);
  }

  @Override
  public ListIterator<GComponent> listIterator() {
    return initComponents.listIterator();
  }

  @Override
  public ListIterator<GComponent> listIterator(int index) {
    return initComponents.listIterator(index);
  }

  @Override
  public boolean remove(Object component) {
    synchronized(this) {
      if(focus == component) {
        focus = focus.parent;
      }
      return initComponents.remove(component) || unInitComponents.remove(component);
    }
  }

  @Override
  public GComponent remove(int index) {
    synchronized(this) {
      return initComponents.remove(index);
    }
  }

  @Override
  public boolean removeAll(Collection<?> components) {
    synchronized(this) {
      return initComponents.removeAll(components);
    }
  }

  @Override
  public boolean retainAll(Collection<?> components) {
    synchronized(this) {
      return initComponents.retainAll(components);
    }
  }

  @Override
  public GComponent set(int arg0, GComponent arg1) {
    return null;
  }

  @Override
  public int size() {
    return unInitComponents.size() + initComponents.size();
  }

  @Override
  public List<GComponent> subList(int start, int end) {
    return initComponents.subList(start, end);
  }

  @Override
  public Object[] toArray() {
    return initComponents.toArray();
  }

  @Override
  public <T> T[] toArray(T[] type) {
    return initComponents.toArray(type);
  }

}
