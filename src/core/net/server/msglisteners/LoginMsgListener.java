package core.net.server.msglisteners;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import core.Scene;
import core.db.User;
import core.db.UserModel;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.msg.ingame.SceneCreateMsg;
import core.net.msg.pregame.LoginMsg;
import core.net.server.PlayerConnection;

public class LoginMsgListener implements MsgListener {

  private final Scene scene;
  
  public LoginMsgListener(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void msgReceived(Object msg, Connection connection) {
    LoginMsg loginMsg = (LoginMsg) msg;
    PlayerConnection player = (PlayerConnection) connection;
    User user = UserModel.findByUsername(loginMsg.username);
    
    if(user != null) {
      player.setAuthenticated(true);
      scene.queueAddActor(player.actor);
      scene.addController(player.controller);
  
      List<ActorCreateMsg> actorInfoList = ActorCreateMsg.actorInfoList(scene);
      System.out.println("Create message sent : ");
      for(ActorCreateMsg acm : actorInfoList) {
        System.out.println(acm.actorClass);
      }
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