package net.server;

import java.io.IOException;

import net.NetworkConnection;
import net.msg.ChatMsg;

import scene.Scene;
import scene.actors.SatelliteActor;
import scene.actors.ShipActor;
import scene.controllers.ServerSatelliteController;
import scene.controllers.ServerShipController;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;


public class ServerConnection extends NetworkConnection {

  private final Scene scene;
  private final Server server = createServer();
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
      populateScene(scene);
      
      super.setAddress();
      online = true;
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private void populateScene(Scene scene) {
    SatelliteActor sat1 = new SatelliteActor(-8, -5, 10);
    scene.addActor(sat1);
    scene.addController(new ServerSatelliteController(sat1, server));
 
    SatelliteActor sat2 = new SatelliteActor(0, 5, 5);
    scene.addActor(sat2);
    scene.addController(new ServerSatelliteController(sat2, server));
    
    ShipActor player = new ShipActor(0,0);
    scene.addPlayer(player);
    scene.addController(new ServerShipController(player, server));
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
