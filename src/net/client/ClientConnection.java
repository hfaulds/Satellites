package net.client;

import java.io.IOException;
import java.net.InetAddress;

import net.NetworkConnection;
import scene.Scene;
import scene.actors.ProjectileActor;

import com.esotericsoftware.kryonet.Client;

public class ClientConnection extends NetworkConnection {

  private final Client client = new Client();
  
  public ClientConnection(Scene scene) {
    super(scene, new ClientListener(scene));
  }

  public void connect(InetAddress address) throws IOException {
    addClasses(client);
    client.start();
    client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
    client.addListener(listener);
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
  public void sendMsg(Object msg) {
    ClientListener listener = (ClientListener) this.listener;
    if(listener != null && listener.isConnected()) {
      listener.connection.sendUDP(msg);
    }
  }

  @Override
  public void fireProjectile(ProjectileActor projectile) {
    super.addActor(projectile);
  }
}
