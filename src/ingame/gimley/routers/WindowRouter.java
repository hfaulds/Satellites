package ingame.gimley.routers;

import ingame.gimley.SceneWindow;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;

import core.Scene;
import core.net.NetworkConnection;

public class WindowRouter extends WindowAdapter {

  private final SceneWindow window;
  private final NetworkConnection connection;
  private final Scene scene;

  public WindowRouter(SceneWindow window, Scene scene, NetworkConnection connection) {
    this.window = window;
    this.scene = scene;
    this.connection = connection;
  }

  @Override
  public void windowDestroyNotify(WindowEvent e) {
    connection.disconnect();
    scene.destroy();
    window.destroy();
  }
}