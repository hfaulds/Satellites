package ingame.gimley;

import ingame.collisions.ShipStationShieldCollisionListener;
import ingame.controllers.PlayerInputController;
import ingame.gimley.components.ChatBox;
import ingame.gimley.components.FPSCounter;
import ingame.gimley.components.PlayerInventory;
import ingame.gimley.components.StationDisplay;
import ingame.gimley.components.StationDockRequest;
import ingame.gimley.listeners.ChatMsgListener;
import ingame.gimley.listeners.StationDockActionListener;
import ingame.gimley.listeners.StationUndockActionListener;
import ingame.gimley.routers.WindowRouter;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import core.Scene;
import core.SceneUpdater;
import core.geometry.Vector2D;
import core.gimley.GFrame;
import core.net.connections.NetworkConnection;
import core.render.Camera;
import core.render.Renderer3D;

public class SceneWindow extends GFrame {
  
  private final Scene scene;
  private final SceneUpdater updater;
  
  private final Camera camera;
  private final Renderer3D renderer3D;
  
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos   = new Vector2D();
  
  public SceneWindow(final Scene scene, final NetworkConnection connection) {
    super(null, scene.username, 800, 800);
    
    this.scene = scene;
    this.camera = new Camera(scene.player.position, width, height);
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
    add(new PlayerInventory(this, scene.player));
    add(new FPSCounter(this, new Vector2D(5, -20)));
    add(stationDockRequest);
    add(stationDisplay);
  }


  private ChatBox setupChatBox(Scene scene, NetworkConnection connection) {
    final ChatBox chatBox = new ChatBox(this, new Vector2D(15, 15), connection);
    connection.addMsgListener(new ChatMsgListener(chatBox));
    return chatBox;
  }

  private void setupStationUI(final StationDockRequest stationDockRequest, final StationDisplay stationDisplay, final Scene scene, final NetworkConnection connection) {
    stationDockRequest.setVisible(false);
    stationDisplay.setVisible(false);
    
    stationDockRequest.dock.addActionListener(new StationDockActionListener(scene.player, stationDockRequest, connection, stationDisplay));
    stationDisplay.undock.addActionListener(new StationUndockActionListener(stationDisplay, connection, scene));
    
    updater.addCollisionListener(new ShipStationShieldCollisionListener(stationDisplay, stationDockRequest));
  }

  
  /* Rendering */
  
  @Override
  public void init(GL2 gl, int width, int height) {
    renderer3D.init(gl);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    updater.tick();
    renderer3D.render(gl, camera);
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
    scene.input.mouseMoved(end, width, height);
  }
  
  @Override
  public void mousePressed(Vector2D mouse, MouseEvent e) {
    super.mousePressed(mouse, e);
    endMousePos._set(startMousePos._set(mouse));
  }
  
  @Override
  public void mouseReleased(Vector2D mouse, MouseEvent e) {
    super.mouseReleased(mouse, e);

    if(e.getButton() == PlayerInputController.FIRE_BUTTON) {
      scene.input.mouseReleased(mouse);
    }
  }
  
  @Override
  public void mouseWheelMoved(MouseEvent e) {
    super.mouseWheelMoved(e);
    camera.zoomOut(e.getWheelRotation());
  } 

  @Override
  public void mouseMoved(Vector2D mouse) {
    super.mouseMoved(mouse);
    scene.input.mouseMoved(mouse, width, height);
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
