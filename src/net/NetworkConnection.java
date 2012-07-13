package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import scene.actors.SatelliteActor;
import scene.actors.ShipActor;

import math.Rotation;
import math.Vector2D;
import net.msg.ActorMsg;
import net.msg.PlayerMsg;
import net.msg.SceneMsg;
import net.msg.ChatMsg;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public abstract class NetworkConnection {
  
  public static final int TIMEOUT = 5000;
  public static final int TCP_PORT = 54555;
  public static final int UDP_PORT = 45444;
 
  private InetAddress address;

  public abstract void disconnect();

  public abstract boolean  isOnline();

  public abstract void sendMessage(ChatMsg message);

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

  protected void addClasses(EndPoint endPoint) {
    Kryo kryo = endPoint.getKryo();
    
    kryo.register(Vector2D.class);
    kryo.register(Rotation.class);
    
    kryo.register(ActorMsg.class);
    kryo.register(SceneMsg.class);
    kryo.register(PlayerMsg.class);
    kryo.register(ChatMsg.class);
    
    kryo.register(SatelliteActor.class);
    kryo.register(ShipActor.class);
    
    kryo.register(ArrayList.class);
    kryo.register(Class.class);
  }
  
  public InetAddress getAddress() {
    return address;
  }
  
  protected void setAddress() {
    setAddress(getIP());
  }
  
  protected void setAddress(InetAddress address) {
    this.address = address;
  }
}