package core.gimley.routers;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;

public class MouseRouter implements MouseListener {

  private final GComponent component;
  private Vector2D dragStart;

  public MouseRouter(GComponent component) {
    this.component = component;
  }

  private Vector2D getVectorFromEvent(MouseEvent e) {
    return new Vector2D(e.getX(), component.height - e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    dragStart = getVectorFromEvent(e);
    component.mousePressed(dragStart, e);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    component.mouseDragged(dragStart, getVectorFromEvent(e), new Vector2D(50,0), e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    component.mouseReleased(getVectorFromEvent(e), e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    component.mouseMoved(getVectorFromEvent(e));
  }

  @Override
  public void mouseWheelMoved(MouseEvent e) {
    component.mouseWheelMoved(e);
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {}
  @Override
  public void mouseEntered(MouseEvent arg0) {}
  @Override
  public void mouseExited(MouseEvent arg0) {}
  
}