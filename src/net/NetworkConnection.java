package net;

import geometry.Rotation;
import geometry.Vector2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.msg.ActorCreateMsg;
import net.msg.ActorUpdateMsg;
import net.msg.ChatMsg;
import net.msg.PlayerUpdateMsg;
import net.msg.SceneCreateMsg;
import scene.Scene;
import scene.actors.Actor;
import scene.actors.Planet1Actor;
import scene.actors.ProjectileActor;
import scene.actors.ShipActor;
import scene.actors.StationActor;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public abstract class NetworkConnection {
  
  public static final int TIMEOUT = 5000;
  public static final int TCP_PORT = 54555;
  public static final int UDP_PORT = 45444;
 
  private InetAddress address;

  public abstract void disconnect();
  public abstract boolean isOnline();
  public abstract void sendMsg(Object msg);
  public abstract void fireProjectile(ProjectileActor projectile);

  protected final Scene scene;
  
  public NetworkConnection(Scene scene) {
    this.scene = scene;
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

  protected void addClasses(EndPoint endPoint) {
    Kryo kryo = endPoint.getKryo();
  
    kryo.register(Vector2D.class);
    kryo.register(Rotation.class);
    
    kryo.register(ActorCreateMsg.class);
    kryo.register(ActorUpdateMsg.class);
    kryo.register(SceneCreateMsg.class);
    kryo.register(PlayerUpdateMsg.class);
    kryo.register(ChatMsg.class);

    kryo.register(ProjectileActor.class);
    kryo.register(Planet1Actor.class);
    kryo.register(ShipActor.class);
    kryo.register(StationActor.class);
    
    kryo.register(ArrayList.class);
    kryo.register(Class.class);
  }
  
  public InetAddress getAddress() {
    return address;
  }
  
  protected void setAddress() {
    this.setAddress(getIP());
  }
  
  protected void setAddress(InetAddress address) {
    this.address = address;
  }
  
  protected void addActor(Actor actor) {
    this.sendMsg(new ActorCreateMsg(actor.position, actor.rotation, actor.id, actor.mass, actor.getClass()));
    this.scene.queueAddActor(actor);
  }
}