package net.server;

import java.io.IOException;

import net.NetworkConnection;
import net.msg.ChatMsg;
import scene.Scene;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class ServerConnection extends NetworkConnection {

  public final Scene scene;
  public final Server server = createServer();
  private boolean online = false;
  
  public ServerConnection(Scene scene) {
    this.scene = scene;
  }

  public boolean create() {
    try {
      super.addClasses(server);
      server.start();
      server.bind(TCP_PORT, UDP_PORT);
      server.addListener(new ServerListener(scene));
      
      super.setAddress();
      online = true;
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private Server createServer() {
    Server server = new Server() {
      protected Connection newConnection() {
        return new PlayerConnection(this);
      }
    };
    return server;
  }

  @Override
  public void disconnect() {
    server.close();
  }

  @Override
  public boolean isOnline() {
    return online;
  }

  @Override
  public void sendMessage(ChatMsg message) {
    server.sendToAllUDP(message);
  }
}
