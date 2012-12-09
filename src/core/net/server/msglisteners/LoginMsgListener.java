package core.net.server.msglisteners;

import java.util.List;

import org.hibernate.Session;

import com.esotericsoftware.kryonet.Connection;

import core.Scene;
import core.db.entities.UserEntity;
import core.db.models.UserModel;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.pregame.LoginMsg;
import core.net.msg.pregame.SceneCreateMsg;
import core.net.server.PlayerConnection;

public class LoginMsgListener implements MsgListener {

  private final Scene scene;
  private final Session session;
  
  public LoginMsgListener(Scene scene, Session session) {
    this.scene = scene;
    this.session = session;
  }

  @Override
  public void msgReceived(Object msg, Connection connection) {
    LoginMsg loginMsg = (LoginMsg) msg;
    PlayerConnection player = (PlayerConnection) connection;
    UserEntity user = UserModel.findByUsername(loginMsg.username, session);
    
    if(user != null) {
      player.setAuthenticated(true);
      scene.forceAddActor(player.actor);
      scene.addController(player.controller);
  
      List<ActorCreateMsg> actorInfoList = ActorCreateMsg.actorInfoList(scene);
      player.sendTCP(new SceneCreateMsg(actorInfoList, player.actor.id));
    } else {
      player.close();
    }
  }

  @Override
  public Class<?> getMsgClass() {
    return LoginMsg.class;
  }

}