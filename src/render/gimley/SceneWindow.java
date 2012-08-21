package render.gimley;

import geometry.Vector2D;
import geometry.Vector3D;
import gui.InGameGUI;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import render.Renderer2D;
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
  private final Renderer2D renderer2D = new Renderer2D();
  private final Renderer3D renderer3D = new Renderer3D();
  
  private Vector3D camera     = new Vector3D(0, 0, ZOOM_DEFAULT);
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos   = new Vector2D();
  private boolean bPanning       = false;
  
  private ChatBox chatBox = new ChatBox(this, new Vector2D(15, 15));
  private ChatBox chatBox2 = new ChatBox(this, new Vector2D(25, 25));
  private ChatBox chatBox3 = new ChatBox(this, new Vector2D(35, 35));
  private FPSCounter fpsCounter = new FPSCounter(this, new Vector2D(5, InGameGUI.HEIGHT - 50));
  
  public SceneWindow(Scene scene) {
    super(null);
    subcomponents.add(chatBox);
    subcomponents.add(chatBox2);
    subcomponents.add(chatBox3);
    subcomponents.add(fpsCounter);
    
    chatBox.openInput();
    chatBox2.openInput();
    chatBox3.openInput();
    
    this.width = 800;
    this.height = 800;
    this.scene = scene;
    
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
  public void init(GLAutoDrawable drawable) {
    renderer3D.init(drawable.getGL().getGL2(), scene);
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    int width = drawable.getWidth();
    int height = drawable.getHeight();
    this.render(drawable.getGL().getGL2(), width, height);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {}
  
  
  
  @Override
  public void render(GL2 gl, int width, int height) {

    if(bPanning) {
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      camera._add(new Vector3D(direction));
    }

    renderer3D.clear(gl);
    renderer3D.render(gl, scene, camera, (double)width/height);
    renderer2D.render(gl, subcomponents, width, height);
  }

  
  

  @Override
  public boolean testClick(Vector2D position) {
    return true;
  }
  
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
    bPanning = false;
  }
  
  @Override
  public void mouseWheelMoved(MouseEvent e) {
    camera.z = Math.max(Math.abs(camera.z - e.getWheelRotation() * ZOOM_RATE), ZOOM_RATE);
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

}
