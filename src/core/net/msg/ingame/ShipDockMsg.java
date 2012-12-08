package core.net.msg.ingame;

public class ShipDockMsg {
  
  private static enum DOCK_STATUS {
    DOCKING,
    UNDOCKING
  }
  
  public static final DOCK_STATUS DOCKING = DOCK_STATUS.DOCKING;
  public static final DOCK_STATUS UNDOCKING = DOCK_STATUS.UNDOCKING;
  
  public final int id;
  public final DOCK_STATUS status;

  public ShipDockMsg() {
    this(0, DOCKING);
  }
  
  public ShipDockMsg(int id, DOCK_STATUS status) {
    this.id = id;
    this.status = status;
  }
  
}
