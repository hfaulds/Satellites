package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import math.Rotation;
import math.Vector2D;
import scene.Scene;
import actors.SatelliteActor;
import actors.ShipActor;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import controllers.server.ServerPlayerController;
import controllers.server.ServerSatelliteController;

public class NetworkConnection {
  
  public static final int TIMEOUT = 5000;
  public static final int TCP_PORT = 54555;
  public static final int UDP_PORT = 45444;
  
  private EndPoint endPoint;
  
  private final Scene scene;
  private InetAddress address;
      
  public NetworkConnection(Scene scene) {
    this.scene = scene;
  }

  public boolean createServer() {
    Server server = new Server() {
      protected Connection newConnection() {
        return new ClientConnection(new ShipActor(0,0));
      }
    };
    addClasses(server);
    
    try {
      server.start();
      server.bind(TCP_PORT, UDP_PORT);
      server.addListener(new ServerListener(scene, server));
      {
        SatelliteActor sat1 = new SatelliteActor(-8, -5, 10);
        scene.addActor(sat1);
        scene.addController(new ServerSatelliteController(sat1, server));
  
        SatelliteActor sat2 = new SatelliteActor(0, 5, 5);
        scene.addActor(sat2);
        scene.addController(new ServerSatelliteController(sat2, server));
        
        scene.addController(new ServerPlayerController(scene.player, server));
      }
      
      address = getIP();
      endPoint = server;
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private InetAddress getIP() {
    try {
      URLConnection con = new URL("http://automation.whatismyip.com/n09230945.asp").openConnection();
      con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
      con.connect();
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      return InetAddress.getByName(in.readLine());
    } catch (IOException e) {
      try {
        return InetAddress.getLocalHost();
      } catch (UnknownHostException e1) {
        return null;
      }
    }
  }

  public boolean createClient() {
    Client client = new Client();
    addClasses(client);
    
    List<InetAddress> addresses = client.discoverHosts(UDP_PORT, TIMEOUT);
    
    if(addresses.size() == 0)
      return false;
    
    InetAddress address = addresses.get(0);
    try {
      client.start();
      client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
      client.addListener(new ClientListener(scene));
      endPoint = client;
      this.address = address;
      
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  private void addClasses(EndPoint endPoint) {
    Kryo kryo = endPoint.getKryo();
    
    kryo.register(Vector2D.class);
    kryo.register(Rotation.class);
    
    kryo.register(ActorInfo.class);
    kryo.register(SceneInfo.class);
    kryo.register(PlayerInfo.class);
    
    kryo.register(SatelliteActor.class);
    kryo.register(ShipActor.class);
    
    kryo.register(ArrayList.class);
    kryo.register(Class.class);
  }
  
  public void disconnect() {
    if(endPoint != null) {
      endPoint.stop();
      scene.actors.clear();
      endPoint = null;
      address = null;
    }
  }

  public boolean isOnline() {
    return endPoint != null;
  }

  public InetAddress getAddress() {
    return address;
  }
}