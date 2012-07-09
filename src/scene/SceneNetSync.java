package scene;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class SceneNetSync {
  
  public static final int TIMEOUT = 5000;
  public static final int PORT = 54555;
  
  private final Client client = new Client();
  private final Server server = new Server();
  private boolean bOnline = false;
  
  private final Scene scene;
      
  public SceneNetSync(Scene scene) {
    this.scene = scene;
  }

  public void connect() {
    List<InetAddress> addresses = client.discoverHosts(PORT, TIMEOUT);
    if(addresses.size() > 0) {
      connectTo(client, addresses.get(0));
    } else {
      createServer();
    }
  }

  private void createServer() {
    System.out.println("Starting server");
    server.start();
    try {
      server.bind(PORT);
      bOnline = true;
      System.out.println("Server creation sucessful");
    } catch (IOException e) {
      System.out.println("Server creation unsucessful");
      e.printStackTrace();
    }
  }

  private void connectTo(Client client, InetAddress address) {
    System.out.println("Connecting to " + address);
    client.start();
    try {
      client.connect(TIMEOUT, address, PORT);
      bOnline = true;
      System.out.println("Connection sucessful");
    } catch (IOException e) {
      System.out.println("Connection unsucessful");
      e.printStackTrace();
    }
  }
  
  public void disconnect() {
    client.stop();
    server.stop();
    bOnline = true;
  }

  public boolean isOnline() {
    return false;
  }
}