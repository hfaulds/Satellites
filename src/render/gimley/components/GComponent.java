package render.gimley.components;

import geometry.Vector2D;
import geometry.Vector3D;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

public abstract class GComponent {

  protected final List<GComponent> subcomponents = new LinkedList<GComponent>();
  
  public final Vector3D position;
  public final GComponent parent;
  
  public int width;
  public int height;
  
  public GComponent(GComponent parent) {
    this(parent, new Vector2D());
  }
  
  public GComponent(GComponent parent, Vector2D position) {
    this.parent = parent;
    this.position = new Vector3D(position);
  }

  public abstract void render(GL2 gl, int width, int height);
  
  public abstract boolean testClick(Vector2D position);
  
  public void mouseClicked(Vector2D click, int button) {
    for(GComponent component : subcomponents)
      if(component.testClick(click))
        component.mouseClicked(click, button);
  }
  
  public void mouseDragged(Vector2D click, int button) {
    for(GComponent component : subcomponents)
      component.mouseDragged(click, button);
  }

  public void mousePressed(Vector2D click, int button) {
    // TODO Auto-generated method stub
    
  }

  public void mouseReleased(Vector2D click, int button) {
    // TODO Auto-generated method stub
    
  }

  public void mouseWheelMoved(int scroll) {
    // TODO Auto-generated method stub
    
  }
  
}
