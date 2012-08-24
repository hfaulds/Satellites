package gimley.routers;

import gimley.SceneWindow;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;

import core.net.connections.NetworkConnection;

public class WindowRouter extends WindowAdapter {

  private final SceneWindow window;
  private final NetworkConnection connection;

  public WindowRouter(SceneWindow window, NetworkConnection connection) {
    this.window = window;
    this.connection = connection;
  }

  @Override
  public void windowDestroyNotify(WindowEvent e) {
    connection.disconnect();
    window.destroy();
  }
}