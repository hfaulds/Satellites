package core.gimley.components;

import java.util.LinkedList;
import java.util.List;


import javax.media.opengl.GL2;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.gimley.ActionListener;
import core.gimley.GComponentList;

public abstract class GComponent {

  public GComponent parent;
  public final GComponentList subcomponents;

  public int width;
  public int height;
  
  public final Vector2D position;
  
  private boolean bDragPossible;
  private Vector2D dragOffset;
  
  private boolean visible = true;

  protected final List<ActionListener> listeners = new LinkedList<ActionListener>();
  
  
  /* Constructors */
  
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
      for(GComponent component : subcomponents)
        if(component.testClick(click))
          return true;
    
    return testClickNonRecursive(click);
  }
  
  public boolean testClickNonRecursive(Vector2D click) {
    return visible
        && click.x >= parent.position.x + position.x 
        && click.y >= parent.position.y + position.y 
        && click.x <= parent.position.x + position.x + width 
        && click.y <= parent.position.y + position.y + height;
  }
  
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
  }

  private Vector2D globalPosition(GComponent component) {
    if(component.parent != null) {
      return component.position.add(globalPosition(component.parent));
    } else {
      return component.position;
    }
  }

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
  }

  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        if(component.bDragPossible) {
          component.mouseDragged(start, end, dragOffset, e); 
          break;
        }
      }
    }
  }
  
  public void mouseMoved(Vector2D mouse) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        component.mouseMoved(mouse);
      }
    }
  }

  public void mouseWheelMoved(MouseEvent e) {
    synchronized(subcomponents) {
      for(GComponent component : subcomponents) {
        component.mouseWheelMoved(e);
      }
    }
  }
  
  
  /* Key Handling */
  
  public void keyPressed(KeyEvent e) {
    for(GComponent component : subcomponents)
      component.keyPressed(e);
  }
  
  public void keyReleased(KeyEvent e) {
    for(GComponent component : subcomponents)
      component.keyReleased(e);
  }


  /* ActionListener Handling */
  
  public void addActionListener(ActionListener listener) {
    this.listeners.add(listener);
  }


}
