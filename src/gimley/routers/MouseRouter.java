package gimley.routers;

import gimley.core.GComponentList;
import gimley.core.components.GComponent;

import java.util.LinkedList;
import java.util.List;

import scene.Scene;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;

public class MouseRouter extends MouseAdapter {

  private final GComponent parent;
  private final GComponentList subcomponents;
  private final Scene scene;

  public MouseRouter(GComponent parent, Scene scene) {
    this.parent = parent;
    this.scene = scene;
    this.subcomponents = parent.subcomponents;
  }

  private GComponent refreshFocus(Vector2D click) {
    GComponent focus = subcomponents.getFocus();
    
    if(!focus.testClick(click) || focus == parent) {
      List<GComponent> componentsHit = new LinkedList<GComponent>();
      
      for(GComponent component : subcomponents) {
        if(component.testClick(click)) {
          componentsHit.add(component);
        }
      }
      
      if(componentsHit.size() > 0) {
        focus = componentsHit.get(0);
      } else {
        focus = parent;
      }
    }

    subcomponents.setFocus(focus);
    return focus;
  }

  private Vector2D getClick(MouseEvent e) {
    return new Vector2D(e.getX(), parent.height - e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Vector2D click = getClick(e);
    refreshFocus(click).mousePressed(click, e);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Vector2D click = getClick(e);
    subcomponents.getFocus().mouseDragged(click, e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    Vector2D click = getClick(e);
    subcomponents.getFocus().mouseReleased(click, e);
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    if(subcomponents.getFocus() == parent)
      scene.input.mouseClicked(e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    scene.input.mouseMoved(e);
  }

  @Override
  public void mouseWheelMoved(MouseEvent e) {
    subcomponents.getFocus().mouseWheelMoved(e);
  }
  
}