package gimley.components;

import gimley.GComponentList;

import javax.media.opengl.GL2;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;

public abstract class GComponent {

  public final GComponent parent;
  public final GComponentList subcomponents;

  public int width;
  public int height;
  
  public final Vector2D position;
  
  private boolean bDragPossible;
  private boolean visible = true;
  
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

  public void render(GL2 gl, int width, int height) {
    subcomponents.render(gl, width, height);
  }

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
      if(component.testClick(click)) {
        component.mouseReleased(click, e);
      }
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

  public void keyReleased(KeyEvent e) {

  }
  
  public void keyPressed(KeyEvent e) {
    
  }

  public void setVisible(boolean visible) {
    this.visible  = visible;
  }

  public boolean getVisible() {
    return this.visible;
  }
}
