package net.msg;

import java.util.ArrayList;
import java.util.List;

public class SceneMsg {
  
  public final int playerID;
  public final List<ActorMsg> actorInfoList;
  
  public SceneMsg(List<ActorMsg> actorInfoList, int playerID) {
    this.actorInfoList = actorInfoList;
    this.playerID = playerID;
  }
  
  public SceneMsg() {
    this.actorInfoList = new ArrayList<ActorMsg>();
    this.playerID = 0;
  }
}
