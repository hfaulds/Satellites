package core.gimley.components;

import java.util.LinkedList;
import java.util.List;


import javax.media.opengl.GL2;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.gimley.GComponentList;
import core.gimley.listeners.ActionListener;
import core.gimley.listeners.MouseListener;

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
  
  public boolean testClick(Vector2D click) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        if(component.testClick(click)) {
          return component.testClick(click);
        }
      }
    }
    
    return testClickNonRecursive(click);
  }
  
  public boolean testClickNonRecursive(Vector2D click) {
    return visible
        && click.x >= parent.position.x + position.x 
        && click.y >= parent.position.y + position.y 
        && click.x <= parent.position.x + position.x + width 
        && click.y <= parent.position.y + position.y + height;
  }
  
  private Vector2D globalPosition(GComponent component) {
    if(component.parent != null) {
      return component.position.add(globalPosition(component.parent));
    } else {
      return component.position;
    }
  }

  @Override
  public void mousePressed(Vector2D click, MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        if(component.testClick(click)) {
          component.bDragPossible = true;
          Vector2D pos = globalPosition(component);
          dragOffset = click.sub(pos);
          component.mousePressed(click, e); 
          break;
        }
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mousePressed(click, e);
    }
  }

  @Override
  public void mouseReleased(Vector2D click, MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        component.bDragPossible = false;
        if(component.testClick(click)) {
          component.mouseReleased(click, e); 
          break;
        }
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mouseReleased(click, e);
    }
  }

  @Override
  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        if(component.bDragPossible) {
          component.mouseDragged(start, end, dragOffset, e); 
          break;
        }
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mouseDragged(start, end , offset, e);
    }
  }

  @Override
  public void mouseMoved(Vector2D mouse) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        component.mouseMoved(mouse);
      }
    }
    for(MouseListener listener : mouseListeners) {
      listener.mouseMoved(mouse);
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
