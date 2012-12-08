package core.net.client.msglisteners;

import ingame.actors.ProjectileActor;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;

public class ActorCreateMsgListener implements MsgListener {
  
  private final Scene scene;

  public ActorCreateMsgListener(Scene scene) {
    this.scene = scene;
  }

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
}