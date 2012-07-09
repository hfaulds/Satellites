package scene;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import math.Rotation;
import math.Vector2D;
import net.ActorInfo;
import net.ClientConnection;
import net.ClientListener;
import net.ServerListener;
import actors.SatelliteActor;
import actors.ShipActor;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import controllers.server.ServerSatelliteController;
import controllers.server.ServerShipController;

public class SceneNetwork {
  
  public static final int TIMEOUT = 5000;
  public static final int TCP_PORT = 54555;
  public static final int UDP_PORT = 45444;
  
  private EndPoint endPoint;
  
  private final Scene scene;
      
  public SceneNetwork(Scene scene) {
    this.scene = scene;
  }

  public void connect() {
    if(createClient() || createServer());
  }

  private boolean createServer() {
    
    Server server = new Server() {
      protected Connection newConnection() {
        return new ClientConnection(new ShipActor(0,0));
      }
    };
    addClasses(server);
    
    System.out.println("Starting server");
    
    try {
      server.start();
      server.bind(TCP_PORT, UDP_PORT);
      server.addListener(new ServerListener(scene));
      
      {
        SatelliteActor sat1 = new SatelliteActor(-8, -5, 10);
        scene.addActor(sat1);
        scene.addController(new ServerSatelliteController(sat1, server));
  
        SatelliteActor sat2 = new SatelliteActor(0, 5, 0, 0, 5);
        scene.addActor(sat2);
        scene.addController(new ServerSatelliteController(sat2, server));
        
        scene.addController(new ServerShipController(scene.player, server));
      }

      endPoint = server;
      System.out.println("Server creation sucessful");
      return true;
    } catch (IOException e) {
      System.out.println("Server creation unsucessful");
      return false;
    }
  }

  private boolean createClient() {
    Client client = new Client();
    addClasses(client);
    
    InetAddress address = client.discoverHost(UDP_PORT, TIMEOUT);
    if(address == null)
      return false;
    
    System.out.println("Connecting to " + address);
    try {
      client.start();
      client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
      client.addListener(new ClientListener(scene));
      endPoint = client;
      
      System.out.println("Connection sucessful");
      
      return true;
    } catch (IOException e) {
      System.out.println("Connection unsucessful");
      return false;
    }
  }

  private void addClasses(EndPoint endPoint) {
    Kryo kryo = endPoint.getKryo();
    kryo.register(Vector2D.class);
    kryo.register(Rotation.class);
    kryo.register(ActorInfo.class);
    kryo.register(ArrayList.class);
    kryo.register(SatelliteActor.class);
    kryo.register(ShipActor.class);
    kryo.register(Class.class);
  }
  
  public void disconnect() {
    if(endPoint != null)
      endPoint.stop();
    endPoint = null;
  }

  public boolean isOnline() {
    return endPoint != null;
  }
}