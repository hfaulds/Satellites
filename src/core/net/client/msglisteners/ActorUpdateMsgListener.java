package core.net.client.msglisteners;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorUpdateMsg;

public class ActorUpdateMsgListener implements MsgListener {
  
  private final Scene scene;

  public ActorUpdateMsgListener(Scene scene) {
    this.scene = scene;
  }

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
}