package net;

import java.util.ArrayList;
import java.util.List;

public class SceneInfo {
  
  public final ActorInfo player;
  public final List<ActorInfo> actorInfoList;
  
  public SceneInfo(List<ActorInfo> actorInfoList, ActorInfo player) {
    this.actorInfoList = actorInfoList;
    this.player = player;
  }
  
  public SceneInfo() {
    this.actorInfoList = new ArrayList<ActorInfo>();
    this.player = null;
  }
}
