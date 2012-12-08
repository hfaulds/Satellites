package core.net.connections;

import ingame.actors.ProjectileActor;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.net.listeners.ClientListener;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.ingame.ActorUpdateMsg;
import core.net.msg.ingame.SceneCreateMsg;
import core.net.msg.ingame.ShipDockMsg;
import core.net.msg.pregame.LoginMsg;

public class ClientConnection extends NetworkConnection {

  private final Client client = new Client();
  private final ClientListener listener;
  
  public ClientConnection(Scene scene, String username) {
    super(scene, username);
    this.listener = new ClientListener(scene);
  }

  public void connect(InetAddress address) throws IOException {
    registerMsgClasses(client);
    setupMsgListeners(scene);
    client.addListener(listener);
    client.start();
    client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
    client.sendTCP(new LoginMsg("player"));
    super.setAddress(address);
  }
  
  public void addMsgListener(MsgListener msgListener) {
    this.listener.addMsgListener(msgListener);
  }

  private void setupMsgListeners(final Scene scene) {
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection connection) {
        SceneCreateMsg sceneInfo = (SceneCreateMsg)msg;
        scene.populate(sceneInfo, connection);
      }

      @Override
      public Class<?> getMsgClass() {
        return SceneCreateMsg.class;
      }
    });
    
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
    if(client.isConnected()) {
      client.sendUDP(msg);
    }
  }

  @Override
  public void fireProjectile(ProjectileActor projectile) {
    super.addActor(projectile);
  }
}
