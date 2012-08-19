package render.gimley;

import geometry.Vector2D;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import render.Renderer3D;
import render.gimley.components.ChatBox;
import render.gimley.components.FPSCounter;
import render.gimley.components.GComponent;
import scene.Scene;
import scene.actors.Planet1Actor;
import scene.actors.ShipActor;
import scene.actors.StationActor;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class SceneWindow extends GComponent implements GLEventListener, MouseListener {

  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 20;
  private static final int PAN_BUTTON   = MouseEvent.BUTTON1;
  
  private final GLWindow glWindow;
  private final FPSAnimator animator;
  
  private final Scene scene;
  private final Renderer3D renderer3D = new Renderer3D();
  
  private Vector2D cameraPos     = new Vector2D();
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos   = new Vector2D();
  private boolean bPanning       = false;
  public double zoom             = ZOOM_DEFAULT;
  
  private GComponent focus = this;
  
  private final List<GComponent> components = new LinkedList<GComponent>(
      Arrays.asList(new ChatBox(), new FPSCounter()));
  
  public SceneWindow(Scene scene) {
    super(new Vector2D());
    this.scene = scene;
    GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
    
    this.glWindow = GLWindow.create(capabilities);
    glWindow.setSize(800, 800);    
    glWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowDestroyNotify(WindowEvent e) {
        animator.stop();
        glWindow.destroy();
      }
    });

    glWindow.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        
        Vector2D clickPosition = new Vector2D(e.getX(), e.getY());
        
        if(!focus.testClick(clickPosition)) {
          List<GComponent> componentsHit = new LinkedList<GComponent>();
          
          for(GComponent component : components) {
            if(component.testClick(clickPosition))
              componentsHit.add(component);
          }
          
          if(componentsHit.size() > 0) {
            Collections.sort(componentsHit, new Comparator<GComponent>() {
              @Override 
              public int compare(GComponent a, GComponent b) {
                return (int) (b.position.z - a.position.z);
              }
            });
            
            changeFocus(components.get(0));
          }
        }
        
      }
    });
    glWindow.addMouseListener(focus);
    
    glWindow.addGLEventListener(this);
    glWindow.setVisible(true);
    
    this.animator = new FPSAnimator(glWindow, 80);
    animator.start();
  }
  
  public void changeFocus(GComponent focus) {
    //TODO change z-axis of items
    glWindow.removeMouseListener(this.focus);
    this.focus = focus;
    glWindow.addMouseListener(focus);
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    int width = drawable.getWidth();
    int height = drawable.getHeight();
    this.render(drawable.getGL().getGL2(), width, height);
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    renderer3D.init(drawable.getGL().getGL2(), scene);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    
    if(bPanning) {
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      cameraPos._add(direction);
    }

    renderer3D.clear(gl);
    renderer3D.preRender(gl, cameraPos , width/height, zoom);
    renderer3D.render(gl, scene);
    
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0, width, height, 0, 0, 1);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glDisable(GL2.GL_LIGHTING);
    gl.glScalef(1, -1, 1);
    gl.glTranslated(0, -height, 0);
    
    Collections.sort(components, new Comparator<GComponent>() {
      @Override 
      public int compare(GComponent a, GComponent b) {
        return (int) (b.position.z - a.position.z);
      }
    });

    for(GComponent component : components) {
      gl.glTranslated(component.position.x, component.position.y, 0);
      component.render(gl, width, height);
    }
    
    gl.glEnable(GL2.GL_LIGHTING);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3,
      int arg4) {
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {}


  
  
  
  
  @Override
  public void mouseDragged(MouseEvent e) {
    bPanning = !(e.getButton() == PAN_BUTTON);
    endMousePos._setFromScreen(e.getX(), e.getY());
  }
  
  @Override
  public void mousePressed(MouseEvent e) {
    bPanning = !(e.getButton() == PAN_BUTTON);
    endMousePos._set(startMousePos._setFromScreen(e.getX(), e.getY()));
  }
  
  @Override
  public void mouseReleased(MouseEvent e) {
    bPanning = (e.getButton() == PAN_BUTTON);
  }
  
  @Override
  public void mouseWheelMoved(MouseEvent e) {
    this.zoom =  Math.max(Math.abs(this.zoom - e.getWheelRotation() * ZOOM_RATE), ZOOM_RATE);
    renderer3D.updateMatrices();
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  
  
  
  
  
  
  public static void main(String ... args) {
    Scene scene = new Scene("");

    ShipActor player = new ShipActor(0, 0);
    scene.addPlayer(player);
    
    Planet1Actor planet = new Planet1Actor(17, 17);
    scene.queueAddActor(planet);
    
    StationActor station = new StationActor(-25, 17);
    scene.queueAddActor(station);
    
    new SceneWindow(scene);
  }

  @Override
  public boolean testClick(Vector2D position) {
    return true;
  }

}
