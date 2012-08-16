package net.server;

import java.io.IOException;

import net.NetworkConnection;
import scene.Scene;
import scene.actors.ProjectileActor;
import scene.controllers.ServerActorController;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class ServerConnection extends NetworkConnection {

  public final Server server = createServer();
  private boolean online = false;
  
  public ServerConnection(Scene scene) {
    super(scene);
  }

  public boolean create() {
    try {
      super.addClasses(server);
      server.start();
      server.bind(TCP_PORT, UDP_PORT);
      server.addListener(new ServerListener(scene, server));
      
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
        return new Player(this);
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
  public void sendMsg(Object msg) {
    server.sendToAllUDP(msg);
  }

  @Override
  public void fireProjectile(ProjectileActor projectile) {
    super.addActor(projectile);
    scene.addController(new ServerActorController(projectile, server));
  }
}
