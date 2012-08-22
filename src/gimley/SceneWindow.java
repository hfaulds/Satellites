package gimley;

import gimley.components.ChatBox;
import gimley.components.FPSCounter;
import gimley.components.GComponent;
import gimley.components.StationDockRequest;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import scene.Scene;
import scene.SceneUpdater;
import scene.actors.Actor;
import scene.actors.ShipActor;
import scene.actors.StationActor;
import scene.collisions.Collision;
import scene.collisions.CollisionListener;


import com.esotericsoftware.kryonet.Connection;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

import core.geometry.Vector2D;
import core.geometry.Vector3D;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.net.msg.MsgListener;
import core.render.Renderer2D;
import core.render.Renderer3D;

public class SceneWindow extends GComponent implements GLEventListener {

  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 20;
  private static final int PAN_BUTTON   = MouseEvent.BUTTON1;
  
  private final FPSAnimator animator;
  
  private final Scene scene;
  private final SceneUpdater updater;
  private final Renderer2D renderer2D = new Renderer2D();
  private final Renderer3D renderer3D = new Renderer3D();
  
  private Vector3D camera        = new Vector3D(0, 0, ZOOM_DEFAULT);
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos   = new Vector2D();
  private boolean bPanning       = false;
  
  public SceneWindow(final Scene scene, final NetworkConnection connection) {
    super(null);
    
    this.width = 800;
    this.height = 800;

    this.scene = scene;
    this.updater = new SceneUpdater(scene);
    this.animator = new FPSAnimator(setupGLWindow(scene), 80);

    subcomponents.add(setupChatBox(scene, connection));
    subcomponents.add(new FPSCounter(this, new Vector2D(5, height - 50)));
    subcomponents.add(setupStationDockRequest(updater));
    
    animator.start();
  }


  /* Setup Graphics */
  
  private GLWindow setupGLWindow(final Scene scene) {
    GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
    final GLWindow window = GLWindow.create(capabilities);
    window.setSize(width, height);    
    window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowDestroyNotify(WindowEvent e) {
        animator.stop();
        window.destroy();
      }
    });
    window.addMouseListener(new MouseRouter(this, scene));
    window.addKeyListener(new KeyRouter(this, scene));
    window.addGLEventListener(this);
    window.setVisible(true);
    return window;
  }

  private ChatBox setupChatBox(Scene scene, NetworkConnection connection) {
    final ChatBox chatBox = new ChatBox(this, new Vector2D(15, 15), scene.username, connection);
    connection.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection reply) {
        chatBox.displayMessage((ChatMsg) msg);
      }

      @Override
      public Class<?> getMsgClass() {
        return ChatMsg.class;
      }
    });
    return chatBox;
  }

  private GComponent setupStationDockRequest(SceneUpdater updater) {
    final GComponent stationDockRequest = new StationDockRequest(this);
    updater.addCollisionListener(new CollisionListener() {

      @SuppressWarnings("unchecked")
      @Override
      public Class<? extends Actor>[] getTypes() {
        return new Class[]{ShipActor.class, StationActor.class};
      }

      @Override
      public void collision(Collision collision) {
        stationDockRequest.setVisible(true);
      }
      
    });
    return stationDockRequest;
  }

  /* Rendering */
  
  @Override
  public void init(GLAutoDrawable drawable) {
    renderer3D.init(drawable.getGL().getGL2(), scene);
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    updater.tick();
    int width = drawable.getWidth();
    int height = drawable.getHeight();
    GL2 gl = drawable.getGL().getGL2();
    
    if(bPanning) {
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      camera._add(new Vector3D(direction));
    }

    renderer3D.clear(gl);
    renderer3D.render(gl, scene, camera, (double)width/height);
    renderer2D.render(gl, subcomponents, width, height);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {}
 

  /* Mouse Handling */
  
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
  
}
