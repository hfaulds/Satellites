package ingame.gimley;

import ingame.actors.PlayerShipActor;
import ingame.collisions.ShipStationShieldCollisionListener;
import ingame.gimley.components.ChatBox;
import ingame.gimley.components.FPSCounter;
import ingame.gimley.components.PlayerInventory;
import ingame.gimley.components.StationDisplay;
import ingame.gimley.components.StationDockRequest;
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
    super(null, connection.username, 800, 800);

    PlayerShipActor player = scene.input.player;
    this.scene = scene;
    this.camera = new Camera(player.position, width, height);
    this.renderer3D = new Renderer3D(scene);
    this.updater = new SceneUpdater(scene);
    this.setupComponents(player, connection);
    this.addWindowListener(new WindowRouter(this, scene, connection));
    this.setVisible(true);
  }
  
  
  /* Components */

  private void setupComponents(PlayerShipActor player, NetworkConnection connection) {
    
    add(ChatBox.createChatBox(this, connection));
    add(PlayerInventory.createPlayerInventory(this, player));
    add(new FPSCounter(this, new Vector2D(5, -20)));
    

    StationDockRequest stationDockRequest = new StationDockRequest(this);
    StationDisplay stationDisplay = new StationDisplay(this);
    setupStationUI(connection, player, stationDockRequest, stationDisplay);
    
    add(stationDockRequest);
    add(stationDisplay);
  }


  private void setupStationUI(NetworkConnection connection,
      PlayerShipActor player, StationDockRequest stationDockRequest,
      StationDisplay stationDisplay) {
    stationDisplay.undock.addActionListener(new StationUndockActionListener(stationDisplay, connection, player));
    stationDockRequest.dock.addActionListener(new StationDockActionListener(player, stationDockRequest, connection, stationDisplay));
    
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
    if(sceneHasFocus()) {
      scene.input.mouseMoved(end, width, height);
    }
  }
  
  @Override
  public void mousePressed(Vector2D mouse, MouseEvent e) {
    super.mousePressed(mouse, e);
    if(sceneHasFocus()) {
      endMousePos._set(startMousePos._set(mouse));
    }
  }
  
  @Override
  public void mouseReleased(Vector2D mouse, MouseEvent e) {
    super.mouseReleased(mouse, e);

    if(sceneHasFocus()) {
      scene.input.mouseReleased(mouse, e);
    }
  }
  
  @Override
  public void mouseWheelMoved(MouseEvent e) {
    super.mouseWheelMoved(e);
    if(sceneHasFocus()) {
      camera.zoomOut(e.getWheelRotation());
    }
  }

  @Override
  public void mouseMoved(Vector2D mouse) {
    super.mouseMoved(mouse);
    if(sceneHasFocus()) {
      scene.input.mouseMoved(mouse, width, height);
    }
  }
  
  
  /* Key Handling */
  
  @Override
  public void keyPressed(KeyEvent e) {
    super.keyPressed(e);
    if(sceneHasFocus()) {
      scene.input.keyPressed(e);
    }
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    super.keyReleased(e);
    if(sceneHasFocus()) {
      scene.input.keyReleased(e);
    }
  }

  private boolean sceneHasFocus() {
    return this.subcomponents.getFocus() == this;
  } 

}
