package pregame;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import core.Scene;
import core.SceneUpdater;
import core.db.models.MapModel;
import core.net.server.ServerConnection;

public class HeadlessServer implements Runnable {

  private SceneUpdater updater;
  private ServerConnection connection;
  private Scene scene;
  private Session session;

  private volatile boolean running = true;

  public HeadlessServer() {
    Configuration configuration = new Configuration().configure();
    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    this.session = sessionFactory.openSession();
    
    
    System.out.println("Server Initializing");
    this.scene = MapModel.findByName("default", session).toScene();
    this.updater = new SceneUpdater(scene);
    
    
    this.connection = new ServerConnection(scene, session, "");
  }

  private void stop() {
    running = false;
    session.close();
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
