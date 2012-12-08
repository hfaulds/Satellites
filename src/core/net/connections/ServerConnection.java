package core.net.connections;

import ingame.actors.ProjectileActor;
import ingame.controllers.ServerActorController;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Actor;
import core.Scene;
import core.geometry.Vector2D;
import core.net.listeners.ServerListener;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.ingame.PlayerUpdateMsg;
import core.net.msg.ingame.ShipDockMsg;

public class ServerConnection extends NetworkConnection {

  private final Server server = createServer();
  private boolean online = false;
  private ServerListener listener;

  public ServerConnection(Scene scene, String username) {
    super(scene, username);
    this.listener = new ServerListener(scene);
  }

  private Server createServer() {
    Server server = new Server() {
      protected Connection newConnection() {
        return new PlayerConnection(ServerConnection.this);
      }
    };
    return server;
  }
  
  public boolean create() {
    try {
      super.registerMsgClasses(server);
      setupMsgListeners(scene);
      
      server.start();
      server.bind(TCP_PORT, UDP_PORT);
      server.addListener(listener);

      super.setAddress();
      online = true;
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public void addMsgListener(MsgListener msgListener) {
    listener.addMsgListener(msgListener);
  }

  private void setupMsgListeners(final Scene scene) {
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection connection) {
        PlayerConnection player = (PlayerConnection) connection;
        player.updateActor((PlayerUpdateMsg) msg);
      }

      @Override
      public Class<?> getMsgClass() {
        return PlayerUpdateMsg.class;
      }
    });
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection connection) {
        sendMsg((ShipDockMsg) msg);
      }

      @Override
      public Class<?> getMsgClass() {
        return ShipDockMsg.class;
      }
    });
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection reply) {
        ActorCreateMsg actorInfo = (ActorCreateMsg) msg;
        if (actorInfo.actorClass.equals(ProjectileActor.class)) {
          ProjectileActor projectile = (ProjectileActor) Actor
              .fromInfo(actorInfo);
          projectile.velocity._set(Vector2D.fromRotation(projectile.rotation)
              ._multiply(ProjectileActor.SPEED));
          scene.queueAddActor(projectile);
          scene.addController(new ServerActorController(projectile,
              ServerConnection.this));
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
    scene.addController(new ServerActorController(projectile, this));
  }

}
