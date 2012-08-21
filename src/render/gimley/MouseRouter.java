package render.gimley;

import geometry.Vector2D;

import java.util.LinkedList;
import java.util.List;

import render.gimley.components.GComponent;
import render.gimley.components.GComponentList;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

public class MouseRouter extends MouseAdapter {

  private final SceneWindow window;
  private final GComponentList subcomponents;

  MouseRouter(SceneWindow window) {
    this.window = window;
    this.subcomponents = window.subcomponents;
  }

  private GComponent refreshFocus(Vector2D click) {
    GComponent focus = subcomponents.getFocus();
    
    boolean testClick = focus.testClick(click);

    if(!testClick || focus == window) {
      List<GComponent> componentsHit = new LinkedList<GComponent>();
      
      for(GComponent component : subcomponents) {
        if(component.testClick(click))
          componentsHit.add(component);
      }
      
      if(componentsHit.size() > 0) {
        focus = componentsHit.get(0);
      } else {
        focus = window;
      }
      
    }
    
    System.out.println(focus);

    subcomponents.setFocus(focus);
    return focus;
  }

  private Vector2D getClick(MouseEvent e) {
    return new Vector2D(e.getX(), window.height - e.getY());
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
  public void mouseWheelMoved(MouseEvent e) {
    subcomponents.getFocus().mouseWheelMoved(e);
  }
  
}