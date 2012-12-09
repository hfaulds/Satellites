package core.net.server.msglisteners;

import ingame.actors.ProjectileActor;
import ingame.controllers.ServerActorController;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.geometry.Vector2D;
import core.net.msg.MsgListener;
import core.net.msg.ingame.ActorCreateMsg;
import core.net.server.ServerConnection;

public class ActorCreateMsgListener implements MsgListener {

  private final ServerConnection serverConnection;
  private final Scene scene;

  public ActorCreateMsgListener(ServerConnection serverConnection, Scene scene) {
    this.serverConnection = serverConnection;
    this.scene = scene;
  }

  @Override
  public void msgReceived(Object msg, Connection reply) {
    ActorCreateMsg actorInfo = (ActorCreateMsg) msg;
    if (actorInfo.actorClass.equals(ProjectileActor.class)) {
      ProjectileActor projectile = (ProjectileActor) Actor.fromMsg(actorInfo);
      projectile.velocity._set(Vector2D.fromRotation(projectile.rotation)._multiply(ProjectileActor.SPEED));
      
      scene.queueAddActor(projectile);
      scene.addController(new ServerActorController(projectile, this.serverConnection));
    }
  }

  @Override
  public Class<?> getMsgClass() {
    return ActorCreateMsg.class;
  }
}