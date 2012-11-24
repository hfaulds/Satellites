import ingame.actors.ShipActor;
import ingame.gimley.components.PlayerInventory;
import core.gimley.GFrame;

public class GimleyTest {

  public static void main(String ... args) {
    GFrame window = new GFrame(null, "test window", 500, 500);
    
    ShipActor player = new ShipActor(1000,100);
    final PlayerInventory inventory = new PlayerInventory(window, player);
    window.add(inventory);
    
    window.setVisible(true);
  }
}
