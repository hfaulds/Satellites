package net.msg;

import java.util.ArrayList;
import java.util.List;

public class SceneInfo {
  
  public final int playerID;
  public final List<ActorInfo> actorInfoList;
  
  public SceneInfo(List<ActorInfo> actorInfoList, int playerID) {
    this.actorInfoList = actorInfoList;
    this.playerID = playerID;
  }
  
  public SceneInfo() {
    this.actorInfoList = new ArrayList<ActorInfo>();
    this.playerID = 0;
  }
}
