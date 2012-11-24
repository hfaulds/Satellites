package core.net.connections;

import ingame.actors.ProjectileActor;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.net.MsgListener;
import core.net.listeners.ClientListener;
import core.net.msg.ActorCreateMsg;
import core.net.msg.ActorUpdateMsg;
import core.net.msg.ShipDockMsg;

public class ClientConnection extends NetworkConnection {

  private final Client client = new Client();
  
  public ClientConnection(Scene scene) {
    super(scene, new ClientListener(scene));
  }

  public void connect(InetAddress address) throws IOException {
    setupKryo(client);
    setuMsgListeners(scene);
    client.start();
    client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
    client.addListener(listener);
    super.setAddress(address);
  }

  private void setuMsgListeners(final Scene scene) {
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection connection) {
        ActorUpdateMsg actorInfo = (ActorUpdateMsg) msg;
        Actor actor = scene.findActor(actorInfo.id);
        
        System.out.println("Something moved " + actorInfo.id + " class ");// + scene.findActor(actorInfo.id).getClass());
        
        if(actor != null) {
          System.out.println("Actor found " + actor.id + " class ");
          actor._update(actorInfo);
        }
      }

      @Override
      public Class<?> getMsgClass() {
        return ActorUpdateMsg.class;
      }
    });
    
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection connection) {
        ActorCreateMsg actorInfo = (ActorCreateMsg) msg;
        if(actorInfo.actorClass.equals(ProjectileActor.class)) {
          scene.queueAddActor(Actor.fromInfo(actorInfo));
        }
      }

      @Override
      public Class<?> getMsgClass() {
        return ActorCreateMsg.class;
      }
    });
    
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection connection) {
        ShipDockMsg dockInfo = (ShipDockMsg)msg;
        Actor ship = scene.findActor(dockInfo.id);
        if(dockInfo.status == ShipDockMsg.DOCKING) {
          ship.setVisible(false);
        } else {
          ship.setVisible(true);
        }
      }
      
      @Override
      public Class<?> getMsgClass() {
        return ShipDockMsg.class;
      }
    });
    
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
