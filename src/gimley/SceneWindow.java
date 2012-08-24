package gimley;




import gimley.components.ChatBox;
import gimley.components.FPSCounter;
import gimley.components.StationDisplay;
import gimley.components.StationDockRequest;
import gimley.core.components.GComponent;
import gimley.core.components.button.ActionListener;
import gimley.routers.KeyRouter;
import gimley.routers.MouseRouter;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import scene.Actor;
import scene.Scene;
import scene.SceneUpdater;
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
import core.net.MsgListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.net.msg.ShipDockMsg;
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
  
  private Vector3D cameraPos     = new Vector3D(0, 0, ZOOM_DEFAULT);
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos   = new Vector2D();
  private boolean bPanning       = false;
  
  public SceneWindow(final Scene scene, final NetworkConnection connection) {
    super(null);
    
    this.width = 800;
    this.height = 800;

    this.scene = scene;
    this.updater = new SceneUpdater(scene);
    this.animator = new FPSAnimator(setupGLWindow(scene, connection), 80);

    subcomponents.add(setupChatBox(scene, connection));
    subcomponents.add(new FPSCounter(this, new Vector2D(5, height - 50)));
    
    setupStationUI(updater, connection);
    
    animator.start();
  }


  /* Setup Graphics */
  
  private GLWindow setupGLWindow(final Scene scene, final NetworkConnection connection) {
    final GLWindow window = GLWindow.create(new GLCapabilities(GLProfile.getDefault()));
    
    window.setSize(width, height);
    
    window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowDestroyNotify(WindowEvent e) {
        connection.disconnect();
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
    final ChatBox chatBox = new ChatBox(this, new Vector2D(15, 15), connection);
    
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

  @SuppressWarnings("unchecked")
  private void setupStationUI(SceneUpdater updater, final NetworkConnection connection) {
    final StationDockRequest stationDockRequest = new StationDockRequest(this);
    final StationDisplay stationDisplay = new StationDisplay(this);
    
    updater.addCollisionListener(new CollisionListener(new Class[]{ShipActor.class, StationActor.class}) {
      @Override
      public void collisionStart(Collision collision) {
        if(collision.a == scene.player) {
          subcomponents.add(stationDockRequest);
        }
      }
      @Override
      public void collisionEnd(Collision collision) {
        if(collision.a == scene.player) {
          subcomponents.remove(stationDockRequest);
        }
      }
    });
    
    stationDockRequest.dock.addActionListener(new ActionListener() {
      @Override
      public void action() {
        scene.input.setActor(null);
        
        Actor player = scene.player;
        
        connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.DOCKING));
        
        player.setVisible(false);
        
        subcomponents.remove(stationDockRequest);
        subcomponents.add(stationDisplay);
      }
    });
    
    stationDisplay.undock.addActionListener(new ActionListener() {
      @Override
      public void action() {
        subcomponents.remove(stationDisplay);
        
        Actor player = scene.player;
        connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.UNDOCKING));

        player.setVisible(true);
        scene.input.setActor(player);
      }
    });
  }

  
  /* Rendering */
  
  @Override
  public void init(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();
    renderer3D.init(gl, scene);
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    updater.tick();
    int width = drawable.getWidth();
    int height = drawable.getHeight();
    GL2 gl = drawable.getGL().getGL2();
    
    if(bPanning) {
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      cameraPos._add(new Vector3D(direction));
    }

    renderer3D.clear(gl);
    renderer3D.render(gl, scene, cameraPos, (double)width/height);
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
    cameraPos.z = Math.max(Math.abs(cameraPos.z - e.getWheelRotation() * ZOOM_RATE), ZOOM_RATE);
    renderer3D.updateMatrices();
  }
  
}
