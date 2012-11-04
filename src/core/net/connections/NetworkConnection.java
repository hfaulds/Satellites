package core.net.connections;


import ingame.actors.ProjectileActor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import core.Actor;
import core.Scene;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.net.MsgListener;
import core.net.listeners.NetworkListener;

public abstract class NetworkConnection {
  
  public static final int TIMEOUT = 5000;
  public static final int TCP_PORT = 54555;
  public static final int UDP_PORT = 45444;
  
  protected final Scene scene;
  protected final NetworkListener listener;

  private InetAddress address;
  
  public abstract void disconnect();
  public abstract boolean isOnline();
  public abstract void sendMsg(Object msg);
  public abstract void fireProjectile(ProjectileActor projectile);

  
  public NetworkConnection(Scene scene, NetworkListener listener) {
    this.scene = scene;
    this.listener = listener;
  }
  
  public void addMsgListener(MsgListener listener) {
    this.listener.addMsgListener(listener);
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

  protected void setupKryo(EndPoint endPoint) {
    Kryo kryo = endPoint.getKryo();
  
    kryo.register(Vector2D.class);
    kryo.register(Rotation.class);
    
    try {
      for(Class<?> klass : getClasses("core.net.msg"))
        kryo.register(klass);
      
      for(Class<?> klass : getClasses("ingame.actors"))
        kryo.register(klass);
      
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
    
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
    this.sendMsg(actor.getCreateMsg());
    this.scene.queueAddActor(actor);
  }
  
  public static ArrayList<Class<?>> getClasses(String packageName) throws ClassNotFoundException {
    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

    String folder = packageName.replace('.', '/');
    
    try {
      File directory = new File(Thread.currentThread().getContextClassLoader().getResource(folder).getFile());
      
      if(directory.exists()) {
        String[] files = directory.list();
        
        for(int i=0; i<files.length; i++) {
          if(files[i].endsWith(".class")) {
            Class<?> klass = Class.forName(packageName + '.' + files[i].substring(0, files[i].length() - 6));
            classes.add(klass);
          }
        }
      }
      
    }
    catch(NullPointerException e) {
      throw new ClassNotFoundException();
    }

    return classes;
  }
  
  public String getUsername() {
    return scene.username;
  }
}