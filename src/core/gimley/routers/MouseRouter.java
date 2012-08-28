package core.gimley.routers;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import core.geometry.Vector2D;
import core.gimley.GComponentList;
import core.gimley.components.GComponent;

public class MouseRouter implements MouseListener {

  private final GComponent parent;
  private final GComponentList subcomponents;
  private Vector2D dragStart;
  private Vector2D dragOffset;

  public MouseRouter(GComponent parent) {
    this.parent = parent;
    this.subcomponents = parent.subcomponents;
  }

  private GComponent refreshFocus(Vector2D click) {
    GComponent focus = subcomponents.getComponentAt(click);
    subcomponents.setFocus(focus);
    return focus;
  }

  private Vector2D getClick(MouseEvent e) {
    return new Vector2D(e.getX(), parent.height - e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Vector2D click = getClick(e);
    GComponent focus = refreshFocus(click);
    dragStart = click;
    dragOffset = click.sub(focus.position);
    focus.mousePressed(click, e);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Vector2D end = getClick(e);
    subcomponents.getFocus().mouseDragged(dragStart, end, dragOffset, e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    Vector2D click = getClick(e);
    subcomponents.getFocus().mouseReleased(click, e);
    refreshFocus(click);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    subcomponents.getFocus().mouseMoved(getClick(e));
  }

  @Override
  public void mouseWheelMoved(MouseEvent e) {
    subcomponents.getFocus().mouseWheelMoved(e);
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {}
  @Override
  public void mouseEntered(MouseEvent arg0) {}
  @Override
  public void mouseExited(MouseEvent arg0) {}
  
}