package core.net.server;

import ingame.actors.ProjectileActor;
import ingame.controllers.ServerActorController;

import java.io.IOException;

import org.hibernate.Session;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Scene;
import core.net.NetworkConnection;
import core.net.msg.MsgListener;
import core.net.server.msglisteners.ActorCreateMsgListener;
import core.net.server.msglisteners.LoginMsgListener;
import core.net.server.msglisteners.PlayerUpdateMsgListener;
import core.net.server.msglisteners.ShipDockMsgListener;

public class ServerConnection extends NetworkConnection {

  private final Server server = createServer();
  
  private boolean online = false;
  private ServerListener listener;

  private final Session session;

  public ServerConnection(Scene scene, Session session, String username) {
    super(scene, username);
    this.listener = new ServerListener(scene);
    this.session = session;
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
      setupMsgListeners(scene, session);
      
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

  private void setupMsgListeners(Scene scene, Session session) {
    listener.addPregameMsgListener(new LoginMsgListener(scene, session));
    this.addMsgListener(new PlayerUpdateMsgListener());
    this.addMsgListener(new ShipDockMsgListener(this));
    this.addMsgListener(new ActorCreateMsgListener(this, scene));
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
    for(Connection connection : server.getConnections()) {
      if(((PlayerConnection)connection).isAuthenticated())
        connection.sendTCP(msg);
    }
  }

  @Override
  public void fireProjectile(ProjectileActor projectile) {
    super.addActor(projectile);
    scene.addController(new ServerActorController(projectile, this));
  }

}
