package pregame;

import java.util.Scanner;

import core.Scene;
import core.SceneUpdater;
import core.net.connections.ServerConnection;

public class HeadlessServer implements Runnable {

  private SceneUpdater updater;
  private ServerConnection connection;
  private Scene scene;

  private volatile boolean running = true;

  public HeadlessServer() {
    System.out.println("Server Initializing");
    this.scene = new Scene();
    this.updater = new SceneUpdater(scene);
    this.connection = new ServerConnection(scene, "");
    connection.setupScene(scene);
  }

  private void stop() {
    running = false;
  }

  @Override
  public void run() {
    if (connection.create()) {
      System.out.println("Server Running");
      while (running) {
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          break;
        }
        updater.tick();
      }
      connection.disconnect();
      scene.destroy();
      System.out.println("Server Closed");
    } else {
      System.out.println("Server Failed");
    }
    System.exit(0);
  }

  public static void main(String... args) {
    HeadlessServer server = new HeadlessServer();
    Thread thread = new Thread(server);
    thread.start();

    Scanner s = new Scanner(System.in);
    s.next();

    server.stop();
  }

}
