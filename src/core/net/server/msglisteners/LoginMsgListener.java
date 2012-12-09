package core.net.server.msglisteners;

import ingame.controllers.ServerActorController;

import java.util.List;

import org.hibernate.Session;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.db.entities.UserEntity;
import core.db.models.UserModel;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.pregame.LoginMsg;
import core.net.msg.pregame.SceneCreateMsg;
import core.net.server.PlayerConnection;
import core.net.server.ServerConnection;

public class LoginMsgListener implements MsgListener {

  private final Scene scene;
  private final Session session;

  private ServerConnection serverConnection;  

  public LoginMsgListener(Scene scene, Session session, ServerConnection connection) {
    this.scene = scene;
    this.session = session;
    this.serverConnection = connection;
  }

  @Override
  public void msgReceived(Object msg, Connection connection) {
    LoginMsg loginMsg = (LoginMsg) msg;
    PlayerConnection player = (PlayerConnection) connection;
    UserEntity user = UserModel.findByUsername(loginMsg.username, session);
    
    
    if(user != null && !serverConnection.userLoggedIn(loginMsg.username)) {
      
      Actor playerActor = user.getActor();
      ServerActorController playerController = new ServerActorController(playerActor, serverConnection);
      
      player.setActor(playerActor);
      player.setController(playerController);
      scene.forceAddActor(playerActor);
      scene.addController(playerController);

      serverConnection.sendMsg(playerActor.getCreateMsg());
      
      List<ActorCreateMsg> actorInfoList = ActorCreateMsg.actorInfoList(scene);
      player.sendTCP(new SceneCreateMsg(actorInfoList, playerActor.id));
      
      player.setAuthenticated(loginMsg.username);
    } else {
      player.close();
    }
  }

  @Override
  public Class<?> getMsgClass() {
    return LoginMsg.class;
  }

}