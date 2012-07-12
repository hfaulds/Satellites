package net.client;

import java.io.IOException;
import java.net.InetAddress;

import net.NetworkConnection;
import net.msg.ChatMsg;
import scene.Scene;

import com.esotericsoftware.kryonet.Client;

public class ClientConnection extends NetworkConnection {

  private final Scene scene;
  private final Client client = new Client();
  
  private ClientListener clientListener; 
  
  public ClientConnection(Scene scene) {
    this.scene = scene;
  }

  public void connect(InetAddress address) throws IOException {
    addClasses(client);
    client.start();
    client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
    this.clientListener = new ClientListener(scene);
    client.addListener(clientListener);
    super.setAddress(address);
  }

  @Override
  public void disconnect() {
    client.stop();
  }

  @Override
  public boolean isOnline() {
    return client.isConnected();
  }

  @Override
  public void sendMessage(ChatMsg message) {
    if(clientListener != null && clientListener.isConnected()) {
      clientListener.connection.sendUDP(message);
    }
  }
}
