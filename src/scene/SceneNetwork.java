package scene;

import java.io.IOException;
import java.net.InetAddress;

import net.ClientConnection;
import net.ActorInfo;
import net.ServerListener;
import actors.ShipActor;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

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
    if(createClient() || createServer()) {
      endPoint.getKryo().register(ActorInfo.class);
    }
  }

  private boolean createServer() {
    
    Server server  = new Server() {
      protected Connection newConnection() {
        return new ClientConnection(new ShipActor(0,0));
      }
    };
    
    System.out.println("Starting server");
    try {
      server.start();
      server.bind(TCP_PORT, UDP_PORT);
      server.addListener(new ServerListener(scene));
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
    InetAddress address = client.discoverHost(UDP_PORT, TIMEOUT);
    System.out.println("Connecting to " + address);
    try {
      client.start();
      client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
      endPoint = client;
      
      System.out.println("Connection sucessful");
      return true;
    } catch (IOException e) {
      System.out.println("Connection unsucessful");
      return false;
    }
  }
  
  public void disconnect() {
    endPoint.stop();
    endPoint = null;
  }

  public boolean isOnline() {
    return endPoint == null;
  }
}