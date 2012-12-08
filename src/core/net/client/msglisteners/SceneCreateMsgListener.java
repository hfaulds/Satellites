package core.net.client.msglisteners;

import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.Scene;
import core.net.msg.MsgListener;
import core.net.msg.ingame.SceneCreateMsg;

public class SceneCreateMsgListener implements MsgListener {
  
  private final Scene scene;

  public SceneCreateMsgListener(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void msgReceived(Object msg, Connection connection) {
    System.out.println("Populating scene");
    SceneCreateMsg sceneInfo = (SceneCreateMsg)msg;
    scene.populate(sceneInfo, connection);

    System.out.println("Actors");
    for(Actor ac : scene.actors) {
      System.out.println(ac.getClass().toString());
    }
    
    System.out.println("Actor queue");
    for(Actor ac : scene.actorqueue) {
      System.out.println(ac.getClass().toString());
    }
  }

  @Override
  public Class<?> getMsgClass() {
    return SceneCreateMsg.class;
  }
}