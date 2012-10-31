package core.gimley.components;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import core.geometry.Vector2D;
import core.gimley.GComponentList;
import core.gimley.listeners.ActionListener;

public abstract class GComponent implements MouseListener {

  public GComponent parent;
  public final GComponentList subcomponents;

  public int width;
  public int height;
  
  public final Vector2D position;
  
  private boolean bDragPossible;
  private Vector2D dragOffset;
  
  private boolean visible = true;

  protected final List<ActionListener> actionListeners = new LinkedList<ActionListener>();
  protected final List<MouseListener> mouseListeners = new LinkedList<MouseListener>();
  
  private GComponent dragComponent;
  private Vector2D dragStart;
  
  
  /* Constructors */
  
  public GComponent(GComponent parent, int width, int height) {
    this(parent, new Vector2D(), width, height);
  }

  public GComponent(GComponent parent, Vector2D position, int width, int height) {
    this.parent = parent;
    this.position = position;
    this.subcomponents = new GComponentList(this, new GComponent[0]);
    this.width = width;
    this.height = height;
  }

  
  /* Subcomponent Access */
  
  public void add(GComponent c) {
    subcomponents.add(c);
  }

  public void remove(GComponent c) {
    subcomponents.remove(c);
  }
  
  
  /* Rendering */
  
  public void init(GL2 gl, int width, int height) {
    subcomponents.init(gl, width, height);
  }
  
  public void render(GL2 gl, int width, int height) {
    subcomponents.render(gl, width, height);
  }
  
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean getVisible() {
    return this.visible;
  }
  
  
  /* Mouse Handling */
  
  public boolean testClick(MouseEvent e) {
    Vector2D screenPosition = getScreenPosition();
    return visible
        && e.getX() >= screenPosition.x 
        && e.getY() >= screenPosition.y 
        && e.getX() <= screenPosition.x + width 
        && e.getY() <= screenPosition.y + height;
  }
  
  public boolean testClick(Vector2D click) {
    Vector2D screenPosition = getScreenPosition();
    return visible
        && click.x >= screenPosition.x 
        && click.y >= screenPosition.y 
        && click.x <= screenPosition.x + width 
        && click.y <= screenPosition.y + height;
  }
  
  
  public Vector2D getScreenPosition() {
    return getScreenPositionOf(this);
  }
  
  private Vector2D getScreenPositionOf(GComponent component) {
    if(component.parent != null) {
      return component.position.add(getScreenPositionOf(component.parent));
    } else {
      return component.position;
    }
  }

  @Override
  public void mouseWheelMoved(MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        component.mouseWheelMoved(e);
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mouseWheelMoved(e);
    }
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mouseDragged(MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        if(component.bDragPossible) {
          dragComponent = component;

          Vector2D end = new Vector2D(e.getX(), e.getY());
          component.mouseDragged(end, dragOffset, e);
          break;
        }
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mouseDragged(e);
    }
  }
  
  private void mouseDragged(Vector2D end, Vector2D offset, MouseEvent e) {
    mouseDragged(dragStart, end, offset, e);
  }
  
  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent arg0) {}

  @Override
  public void mouseExited(MouseEvent arg0) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        component.mouseMoved(e);
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mouseMoved(e);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.dragStart = new Vector2D(e.getX(), e.getY());
    dragComponent = null;
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        if(component.testClick(e)) {
          component.bDragPossible = true;
          component.mousePressed(e);
          dragOffset = getScreenPositionOf(component).sub(e.getX(), e.getY());
          break;
        }
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mousePressed(e);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    synchronized(subcomponents) {
      if(dragComponent == null) {
        for(GComponent component : subcomponents) {
          component.bDragPossible = false;
          if(component.testClick(e)) {
            component.mouseReleased(e); 
            break;
          }
        }
      } else {
        dragComponent.mouseReleased(e);
      }
      dragComponent = null;
    }
    for(MouseListener listener : mouseListeners) {
      listener.mouseReleased(e);
    }
  }

  
  
  /* Key Handling */
  
  public void keyPressed(KeyEvent e) {
    for(GComponent component : subcomponents) {
      component.keyPressed(e);
    }
  }
  
  public void keyReleased(KeyEvent e) {
    for(GComponent component : subcomponents) {
      component.keyReleased(e);
    }
  }


  /* ActionListener Handling */
  
  public void addActionListener(ActionListener listener) {
    this.actionListeners.add(listener);
  }
  
  public void addMouseListener(MouseListener listener) {
    this.mouseListeners.add(listener);
  }

}
