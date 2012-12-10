package core.net.server.msglisteners;

import ingame.controllers.server.ServerShipController;

import org.hibernate.Session;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.db.entities.UserEntity;
import core.db.models.UserModel;
import core.net.msg.MsgListener;
import core.net.msg.pregame.LoginMsg;
import core.net.server.PlayerConnection;
import core.net.server.ServerConnection;

public class LoginMsgListener implements MsgListener {

  private final Session session;
  private final ServerConnection serverConnection;  

  public LoginMsgListener(Session session, ServerConnection connection) {
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
      ServerShipController playerController = new ServerShipController(playerActor, serverConnection);
      
      player.setUsername(loginMsg.username);
      player.setActor(playerActor);
      player.setController(playerController);
      
      serverConnection.addPlayer(this, loginMsg, player);
    } else {
      player.close();
    }
  }

  @Override
  public boolean handlesMsg(Object info) {
    return info instanceof LoginMsg;
  }

}