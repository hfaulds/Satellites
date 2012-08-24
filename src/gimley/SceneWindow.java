package gimley;

import gimley.components.ChatBox;
import gimley.components.FPSCounter;
import gimley.components.StationDisplay;
import gimley.components.StationDockRequest;
import gimley.core.ActionListener;
import gimley.core.GFrame;
import gimley.routers.KeyRouter;
import gimley.routers.MouseRouter;
import gimley.routers.WindowRouter;

import javax.media.opengl.GL2;

import scene.Actor;
import scene.Scene;
import scene.SceneUpdater;
import scene.actors.ShipActor;
import scene.actors.StationActor;
import scene.collisions.Collision;
import scene.collisions.CollisionListener;

import com.esotericsoftware.kryonet.Connection;
import com.jogamp.newt.event.MouseEvent;

import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.geometry.Vector3D;
import core.net.MsgListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.net.msg.ShipDockMsg;
import core.render.Renderer3D;

public class SceneWindow extends GFrame {

  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 20;
  private static final int PAN_BUTTON   = MouseEvent.BUTTON1;
  
  private final Scene scene;
  private final SceneUpdater updater;
  
  private final Renderer3D renderer3D = new Renderer3D();
  
  private Vector3D cameraPos     = new Vector3D(0, 0, ZOOM_DEFAULT);
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos   = new Vector2D();
  private boolean bPanning       = false;
  
  
  public SceneWindow(final Scene scene, final NetworkConnection connection) {
    super(null, 800, 800);

    this.scene = scene;
    this.updater = new SceneUpdater(scene);
    
    setupListeners(scene, connection);
    setupComponents(scene, connection);
    
    setVisible(true);
  }
  
  
  /* Listeners */

  private void setupListeners(Scene scene, NetworkConnection connection) {
    addWindowListener(new WindowRouter(this, connection));
    addMouseListener(new MouseRouter(this, scene));
    addKeyListener(new KeyRouter(this, scene));
  }


  /* Components */

  private void setupComponents(final Scene scene, final NetworkConnection connection) {
    StationDockRequest stationDockRequest = new StationDockRequest(this);
    StationDisplay stationDisplay = new StationDisplay(this);
    
    setupStationUI(stationDockRequest, stationDisplay, connection);
    
    add(setupChatBox(scene, connection));
    add(new FPSCounter(this, new Vector2D(5, -20)));
    add(stationDockRequest);
    add(stationDisplay);
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
  private void setupStationUI(final StationDockRequest stationDockRequest, final StationDisplay stationDisplay, final NetworkConnection connection) {
    stationDockRequest.setVisible(false);
    stationDisplay.setVisible(false);
    
    updater.addCollisionListener(
      new CollisionListener(new Class[]{ShipActor.class, StationActor.class}) {
        @Override
        public void collisionStart(Collision collision) {
          if(collision.a == scene.player) {
            stationDockRequest.setVisible(true);
            stationDisplay.setStation((StationActor)collision.b);
          }
        }
        @Override
        public void collisionEnd(Collision collision) {
          if(collision.a == scene.player) {
            stationDockRequest.setVisible(false);
          }
        }
      }
    );
    
    stationDockRequest.dock.addActionListener(new ActionListener() {
      @Override
      public void action() {
        scene.input.setActor(null);
        
        Actor player = scene.player;
        StationActor station = stationDisplay.getStation();
        player.velocity._set(new Vector2D());
        player.spin._set(new Rotation());
        player.position._set(station.position.add(new Vector2D(station.shieldRadius, 0)));
        
        connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.DOCKING));
        
        player.setVisible(false);
        
        stationDockRequest.setVisible(false);
        stationDisplay.setVisible(true);
      }
    });
    
    stationDisplay.undock.addActionListener(new ActionListener() {
      @Override
      public void action() {
        Actor player = scene.player;
        connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.UNDOCKING));

        stationDisplay.setVisible(false);
        player.setVisible(true);
        scene.input.setActor(player);
      }
    });
  }

  
  /* Rendering */
  
  @Override
  public void init(GL2 gl, int width, int height) {
    renderer3D.init(gl, scene);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    updater.tick();
    
    if(bPanning) {
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      cameraPos._add(new Vector3D(direction));
    }

    renderer3D.render(gl, scene, cameraPos, (double)width/height);
  }
  

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
