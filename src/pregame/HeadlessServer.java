package pregame;

import ingame.actors.Planet001Actor;
import ingame.actors.StationActor;
import ingame.controllers.ServerActorController;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import core.Scene;
import core.SceneUpdater;
import core.net.server.ServerConnection;

public class HeadlessServer implements Runnable {

  private SceneUpdater updater;
  private ServerConnection connection;
  private Scene scene;

  private volatile boolean running = true;

  public HeadlessServer() {
    System.out.println("Server Initializing");
    this.scene = new Scene();
    this.updater = new SceneUpdater(scene);
    
    Configuration configuration = new Configuration().configure();
    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    Session session = sessionFactory.openSession();
    
    this.connection = new ServerConnection(scene, session, "");
    
    Planet001Actor planet = new Planet001Actor(50, 50);
    scene.forceAddActor(planet);
    scene.addController(new ServerActorController(planet, connection));

    StationActor station = new StationActor(-35, 17);
    scene.forceAddActor(station);
    scene.addController(new ServerActorController(station, connection));
    
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
