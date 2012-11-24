package ingame.gimley;

import ingame.actors.ShipActor;
import ingame.actors.StationActor;
import ingame.actors.StationShieldActor;
import ingame.controllers.PlayerInputController;
import ingame.gimley.components.ChatBox;
import ingame.gimley.components.FPSCounter;
import ingame.gimley.components.PlayerInventory;
import ingame.gimley.components.StationDisplay;
import ingame.gimley.components.StationDockRequest;
import ingame.gimley.routers.WindowRouter;

import javax.media.opengl.GL2;

import com.esotericsoftware.kryonet.Connection;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import core.Actor;
import core.Scene;
import core.SceneUpdater;
import core.collisions.Collision;
import core.collisions.CollisionListener;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.geometry.Vector3D;
import core.gimley.GFrame;
import core.gimley.actions.ActionEvent;
import core.gimley.listeners.ActionListener;
import core.net.MsgListener;
import core.net.connections.NetworkConnection;
import core.net.msg.ChatMsg;
import core.net.msg.ShipDockMsg;
import core.render.Renderer3D;

public class SceneWindow extends GFrame {

  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 20;
  private static final int PAN_BUTTON   = MouseEvent.BUTTON3;
  
  private final Scene scene;
  private final SceneUpdater updater;
  private final Renderer3D renderer3D;
  
  private Vector3D cameraPos     = new Vector3D(0, 0, ZOOM_DEFAULT);
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos   = new Vector2D();
  private boolean bPanning       = false;
  
  public SceneWindow(final Scene scene, final NetworkConnection connection) {
    super(null, scene.username, 800, 800);
    
    this.scene = scene;
    this.renderer3D = new Renderer3D(scene);
    this.updater = new SceneUpdater(scene);
    
    addWindowListener(new WindowRouter(this, scene, connection));
    setupComponents(scene, connection);
    
    setVisible(true);
  }
  
  
  /* Components */

  private void setupComponents(final Scene scene, final NetworkConnection connection) {
    StationDockRequest stationDockRequest = new StationDockRequest(this);
    StationDisplay stationDisplay = new StationDisplay(this);
    
    setupStationUI(stationDockRequest, stationDisplay, scene, connection);
    
    add(setupChatBox(scene, connection));
    add(setupInventory(scene));
    add(new FPSCounter(this, new Vector2D(5, -20)));
    add(stationDockRequest);
    add(stationDisplay);
  }


  private PlayerInventory setupInventory(final Scene scene) {
    final PlayerInventory inventory = new PlayerInventory(this, scene.player);
    inventory.setVisible(false);
    return inventory;
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
  private void setupStationUI(final StationDockRequest stationDockRequest, final StationDisplay stationDisplay, final Scene scene, final NetworkConnection connection) {
    stationDockRequest.setVisible(false);
    stationDisplay.setVisible(false);
    
    updater.addCollisionListener(
      new CollisionListener(new Class[]{ShipActor.class, StationShieldActor.class}) {
        @Override
        public void collisionStart(Collision collision) {
          if(collision.a == scene.player) {
            stationDockRequest.setVisible(true);
            stationDisplay.setStation((StationActor)((StationShieldActor)collision.b).parent);
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
      public void action(ActionEvent action) {
        scene.input.setActor(null);
        
        Actor player = scene.player;
        StationActor station = stationDisplay.getStation();
        
        player.velocity._set(new Vector2D());
        player.spin._set(new Rotation());
        player.position._set(station.position.add(new Vector2D(20, 0)));
        player.setVisible(false);
        
        connection.sendMsg(new ShipDockMsg(player.id, ShipDockMsg.DOCKING));
        
        stationDockRequest.setVisible(false);
        stationDisplay.setVisible(true);
        subcomponents.setFocus(stationDisplay);
      }
    });
    
    stationDisplay.undock.addActionListener(new ActionListener() {
      @Override
      public void action(ActionEvent action) {
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
    renderer3D.init(gl);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    updater.tick();
    
    if(bPanning) {
      //System.out.println(endMousePos);
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      cameraPos._add(new Vector3D(direction));
    }

    renderer3D.render(gl, cameraPos, (double)width/height);
  }
  

  /* Mouse Handling */
  
  @Override
  public boolean testClick(Vector2D position) {
    return true;
  }
  
  @Override
  public void mouseDragged(Vector2D start, Vector2D end, Vector2D offset, MouseEvent e) {
    super.mouseDragged(start, end, offset, e);
    endMousePos._set(end);
    scene.input.mouseMoved(end);
  }
  
  @Override
  public void mousePressed(Vector2D mouse, MouseEvent e) {
    super.mousePressed(mouse, e);
    bPanning = e.getButton() == PAN_BUTTON;
    endMousePos._set(startMousePos._set(mouse));
  }
  
  @Override
  public void mouseReleased(Vector2D mouse, MouseEvent e) {
    super.mouseReleased(mouse, e);
    bPanning = false;
    if(e.getButton() == PlayerInputController.FIRE_BUTTON) {
      scene.input.mouseReleased(mouse);
    }
  }
  
  @Override
  public void mouseWheelMoved(MouseEvent e) {
    super.mouseWheelMoved(e); 
    cameraPos.z = Math.max(Math.abs(cameraPos.z - e.getWheelRotation() * ZOOM_RATE), ZOOM_RATE);
    renderer3D.updateMatrices();
  } 

  @Override
  public void mouseMoved(Vector2D mouse) {
    super.mouseMoved(mouse);
    scene.input.mouseMoved(mouse);
  }
  
  
  /* Key Handling */
  
  @Override
  public void keyPressed(KeyEvent e) {
    super.keyPressed(e);
    scene.input.keyPressed(e);
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    super.keyReleased(e);
    scene.input.keyReleased(e);
  }
  
}
