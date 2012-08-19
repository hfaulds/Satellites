package render.gimley.components;

import geometry.Vector2D;
import geometry.Vector3D;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public abstract class GComponent implements MouseListener {
  
  public final Vector3D position;
  
  public GComponent(Vector2D position) {
    this.position = new Vector3D(position);
  }

  public abstract void render(GL2 gl, int width, int height);
  
  public abstract boolean testClick(Vector2D position);
  
  @Override
  public void mouseClicked(MouseEvent e) {}
  @Override
  public void mouseDragged(MouseEvent e) {}
  @Override
  public void mouseEntered(MouseEvent e) {}
  @Override
  public void mouseExited(MouseEvent e) {}
  @Override
  public void mouseMoved(MouseEvent e) {}
  @Override
  public void mousePressed(MouseEvent e) {}
  @Override
  public void mouseReleased(MouseEvent e) {}
  @Override
  public void mouseWheelMoved(MouseEvent e) {}
  
}
