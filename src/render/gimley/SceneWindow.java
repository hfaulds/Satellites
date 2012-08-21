package render.gimley;

import geometry.Vector2D;
import gui.InGameGUI;


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

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class SceneWindow extends GComponent implements GLEventListener {

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
  
  
  public SceneWindow(Scene scene) {
    super(null);
    this.width = 800;
    this.height = 800;
    this.scene = scene;
    
    ChatBox chatBox = new ChatBox(this, new Vector2D(15, 10));
    chatBox.openInput();
    subcomponents.add(chatBox);
    
    subcomponents.add(new FPSCounter(this, new Vector2D(5, InGameGUI.HEIGHT - 50)));
    
    GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
    glWindow = GLWindow.create(capabilities);
    glWindow.setSize(width, height);    
    glWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowDestroyNotify(WindowEvent e) {
        animator.stop();
        glWindow.destroy();
      }
    });

    glWindow.addMouseListener(new MouseRouter(this));
    
    glWindow.addGLEventListener(this);
    glWindow.setVisible(true);
    
    this.animator = new FPSAnimator(glWindow, 80);
    animator.start();
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
    gl.glScalef(1, -1, 1);
    gl.glTranslated(0, -height, 0);
    
    gl.glDisable(GL2.GL_LIGHTING);
    gl.glDisable(GL2.GL_CULL_FACE);
    gl.glDisable(GL2.GL_DEPTH_TEST);
    
    for(GComponent component : subcomponents) {
      component.render(gl, width, height);
    }
    
    gl.glEnable(GL2.GL_CULL_FACE);
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_LIGHTING);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3,
      int arg4) {
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {}

  
  
  
  @Override
  public void mouseDragged(Vector2D click, MouseEvent e) {
    bPanning = !(e.getButton() == PAN_BUTTON);
    endMousePos._set(click);
  }
  
  @Override
  public void mousePressed(Vector2D click, MouseEvent e) {
    bPanning = !(e.getButton() == PAN_BUTTON);
    endMousePos._set(startMousePos._set(click));
  }
  
  @Override
  public void mouseReleased(Vector2D click, MouseEvent e) {
    bPanning = (e.getButton() == PAN_BUTTON);
  }
  
  @Override
  public void mouseWheelMoved(MouseEvent e) {
    this.zoom =  Math.max(Math.abs(this.zoom - e.getWheelRotation() * ZOOM_RATE), ZOOM_RATE);
    renderer3D.updateMatrices();
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
