package net.msg;

import java.util.ArrayList;
import java.util.List;

public class SceneMsg {
  
  public final int playerID;
  public final List<ActorCreateMsg> actorInfoList;
  
  public SceneMsg(List<ActorCreateMsg> actorInfoList, int playerID) {
    this.actorInfoList = actorInfoList;
    this.playerID = playerID;
  }
  
  public SceneMsg() {
    this.actorInfoList = new ArrayList<ActorCreateMsg>();
    this.playerID = 0;
  }
}
