package gimley.core;

import gimley.core.components.GComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.media.opengl.GL2;

public class GComponentList implements List<GComponent> {

  private final ArrayList<GComponent> initComponents = new ArrayList<GComponent>();
  private final ArrayList<GComponent> unInitComponents = new ArrayList<GComponent>();
  
  private static final double MAX_COMPONENTS = 15;
  private GComponent focus;
  
  public GComponentList(GComponent focus, GComponent ... components) {
    unInitComponents.addAll(Arrays.asList(components));
    this.focus = focus;
  }
  
  public GComponent getFocus() {
    return focus;
  }
  
  public void setFocus(GComponent focus) {
    if(focus.getVisible()) {
      if(initComponents.contains(focus)) {
        initComponents.remove(focus);
        initComponents.add(focus);
      }
      
      this.focus = focus;
    } else {
      setFocus(focus.parent);
    }
  }

  private void initComponents(GL2 gl, int width, int height) {
    for(GComponent component : unInitComponents)
      component.init(gl, width, height);
    
    initComponents.addAll(unInitComponents);
    unInitComponents.clear();
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
    return unInitComponents.add(component);
  }
  
  @Override
  public void add(int index, GComponent component) {
    unInitComponents.add(index, component);
  }

  @Override
  public boolean addAll(Collection<? extends GComponent> components) {
    return unInitComponents.addAll(components);
  }

  @Override
  public boolean addAll(int index, Collection<? extends GComponent> components) {
    return unInitComponents.addAll(index, components);
  }

  @Override
  public void clear() {
    unInitComponents.clear();
    initComponents.clear();
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
    if(focus == component)
      focus = focus.parent;
    
    return initComponents.remove(component) || unInitComponents.remove(component);
  }

  @Override
  public GComponent remove(int index) {
    return initComponents.remove(index);
  }

  @Override
  public boolean removeAll(Collection<?> components) {
    return initComponents.removeAll(components);
  }

  @Override
  public boolean retainAll(Collection<?> components) {
    return initComponents.retainAll(components);
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
