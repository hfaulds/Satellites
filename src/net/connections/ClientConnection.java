package net.connections;

import java.io.IOException;
import java.net.InetAddress;

import net.listeners.ClientListener;
import net.msg.ActorCreateMsg;
import net.msg.ActorUpdateMsg;
import net.msg.MsgListener;

import scene.Scene;
import scene.actors.Actor;
import scene.actors.ProjectileActor;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

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
        
        if(actor != null) {
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
