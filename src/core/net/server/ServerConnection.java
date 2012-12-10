package core.net.server;

import ingame.actors.ProjectileActor;
import ingame.controllers.server.ServerActorController;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Actor;
import core.Scene;
import core.net.NetworkConnection;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.pregame.LoginMsg;
import core.net.msg.pregame.SceneCreateMsg;
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
        return new PlayerConnection();
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
    listener.addPregameMsgListener(new LoginMsgListener(session, this));
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

  public boolean userLoggedIn(String username) {
    for(Connection connection : server.getConnections()) {
      PlayerConnection player = ((PlayerConnection)connection);
      if(player.isAuthenticated() && player.getUsername().equals(username))
        return true;
    }
    return false;
  }

  public void addPlayer(LoginMsgListener loginMsgListener, LoginMsg loginMsg, PlayerConnection player) {
    Actor playerActor = player.getActor();
    
    scene.forceAddActor(playerActor);
    scene.addController(player.getController());
  
    sendMsg(playerActor.getCreateMsg());
    
    List<ActorCreateMsg> actorInfoList = ActorCreateMsg.actorInfoList(scene);
    player.sendTCP(new SceneCreateMsg(actorInfoList, playerActor.id));
    
    player.setAuthenticated(true);
  }

}
