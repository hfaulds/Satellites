package core.net.connections;


import java.io.IOException;

import scene.Actor;
import scene.Scene;
import scene.actors.Planet001Actor;
import scene.actors.ProjectileActor;
import scene.actors.ShipActor;
import scene.actors.StationActor;
import scene.controllers.ServerActorController;
import scene.controllers.ServerShipController;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.geometry.Vector2D;
import core.net.MsgListener;
import core.net.Player;
import core.net.listeners.ServerListener;
import core.net.msg.ActorCreateMsg;
import core.net.msg.PlayerUpdateMsg;
import core.net.msg.ShipDockMsg;

public class ServerConnection extends NetworkConnection {

  private final Server server = createServer();
  private boolean online = false;
  
  public ServerConnection(Scene scene) {
    super(scene, new ServerListener(scene));
  }

  public boolean create() {
    try {
      super.setupKryo(server);
      setupMsgListeners(scene);
      server.start();
      server.bind(TCP_PORT, UDP_PORT);
      server.addListener(listener);
      this.addMsgListener(new MsgListener() {
        @Override
        public void msgReceived(Object msg, Connection connection) {
          ((Player)connection).updateActor((PlayerUpdateMsg)msg);
        }
        @Override
        public Class<?> getMsgClass() {
          return PlayerUpdateMsg.class;
        }
      });
      this.addMsgListener(new MsgListener() {
        @Override
        public void msgReceived(Object msg, Connection connection) {
          sendMsg((ShipDockMsg)msg);
        }
        @Override
        public Class<?> getMsgClass() {
          return ShipDockMsg.class;
        }
      });
      super.setAddress();
      online = true;
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private void setupMsgListeners(final Scene scene) {
    
    this.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object msg, Connection reply) {
        ActorCreateMsg actorInfo = (ActorCreateMsg) msg;
        if(actorInfo.actorClass.equals(ProjectileActor.class)) {
          ProjectileActor projectile = (ProjectileActor) Actor.fromInfo(actorInfo);
          projectile.velocity._set(Vector2D.fromRotation(projectile.rotation)._mult(ProjectileActor.SPEED));
          scene.queueAddActor(projectile);
          scene.addController(new ServerActorController(projectile, ServerConnection.this));
        }
      }

      @Override
      public Class<?> getMsgClass() {
        return ActorCreateMsg.class;
      }
    });
    
    ShipActor player = new ShipActor(0, 0);
    scene.addPlayer(player);
    scene.addController(new ServerShipController(player, this));
    
    Planet001Actor planet = new Planet001Actor(17, 17);
    scene.queueAddActor(planet);
    scene.addController(new ServerActorController(planet, this));
    
    StationActor station = new StationActor(-25, 17);
    scene.queueAddActor(station);
    scene.addController(new ServerActorController(station, this));
  }


  private Server createServer() {
    Server server = new Server() {
      protected Connection newConnection() {
        return new Player(ServerConnection.this);
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
    scene.addController(new ServerActorController(projectile, this));
  }
}
