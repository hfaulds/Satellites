package core.net.msg;

import java.util.ArrayList;
import java.util.List;

public class SceneCreateMsg {
  
  public final int playerID;
  public final List<ActorCreateMsg> actorInfoList;
  
  public SceneCreateMsg(List<ActorCreateMsg> actorInfoList, int playerID) {
    this.actorInfoList = actorInfoList;
    this.playerID = playerID;
  }
  
  public SceneCreateMsg() {
    this(new ArrayList<ActorCreateMsg>(), 0);
  }
}
