package net;

import java.util.ArrayList;
import java.util.List;

import scene.Scene;
import actors.Actor;

public class SceneInfo {
  
  public final int playerID = Actor.nextID();
  public final List<ActorInfo> actorInfoList;
  
  public SceneInfo(Scene scene) {
    this.actorInfoList = ActorInfo.actorInfoList(scene.actors);
  }
  
  public SceneInfo() {
    this.actorInfoList = new ArrayList<ActorInfo>();
  }
}
