package render.gimley.components;

import geometry.Vector2D;

import javax.media.opengl.GL2;

import render.gimley.GComponentList;

import com.jogamp.newt.event.MouseEvent;

public abstract class GComponent {

  public final GComponent parent;
  public final GComponentList subcomponents;

  public int width;
  public int height;
  
  public final Vector2D position;
  
  private boolean bDragPossible;
  
  public GComponent(GComponent parent) {
    this(parent, new Vector2D());
  }
  
  public GComponent(GComponent parent, Vector2D position) {
    this(parent, new GComponent[0], position);
  }

  public GComponent(GComponent parent, GComponent[] subcomponents) {
    this(parent, subcomponents, new Vector2D());
  }
  
  public GComponent(GComponent parent, GComponent[] subcomponents, Vector2D position) {
    this.parent = parent;
    this.subcomponents = new GComponentList(this, subcomponents);
    this.position = position;
  }

  public abstract void render(GL2 gl, int width, int height);

  public boolean testClick(Vector2D click) {

    for(GComponent component : subcomponents)
      if(component.testClick(click))
        return true;
    
    return click.x >= position.x 
        && click.y >= position.y 
        && click.x <= position.x + width 
        && click.y <= position.y + height;
  }
  
  public void mousePressed(Vector2D click, MouseEvent e) {
    for(GComponent component : subcomponents)
      if(component.testClick(click)) {
        component.bDragPossible = true;
        component.mousePressed(click, e);
      }
  }

  public void mouseReleased(Vector2D click, MouseEvent e) {
    for(GComponent component : subcomponents) {
      component.bDragPossible = false;
      component.mouseReleased(click, e);
    }
  }

  public void mouseDragged(Vector2D click, MouseEvent e) {
    for(GComponent component : subcomponents)
      if(component.bDragPossible)
        component.mouseDragged(click, e);
  }

  public void mouseWheelMoved(MouseEvent e) {
    for(GComponent component : subcomponents)
      component.mouseWheelMoved(e);
  }
  
}
