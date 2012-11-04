import ingame.gimley.components.ChatBox;
import core.geometry.Vector2D;
import core.gimley.GFrame;

public class GimleyTest {

  public static void main(String ... args) {
    GFrame window = new GFrame(null, "test window", 500, 500);
    
    
    ChatBox chat = new ChatBox(window, new Vector2D(20,20), null);
    window.add(chat);
    window.setVisible(true);
  }
}
