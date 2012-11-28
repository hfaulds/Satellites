package ingame.gimley.components;

import ingame.actors.StationActor;

import java.util.LinkedList;

import javax.media.opengl.GL2;

import core.Item;
import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.gimley.components.GPanel;
import core.gimley.components.buttons.GButton;
import core.render.Renderer2D;

public class StationDisplay extends GPanel {

  private static final int WIDTH = 750;
  private static final int HEIGHT = 600;

  private StationActor station;

  public final GButton undock = new GButton(this, new Vector2D(675, 10), 65, 20, "UNDOCK");
  public final LinkedList<Item> inventory = new LinkedList<Item>();
  
  public StationDisplay(GComponent parent) {
    super(parent, "Station", new Vector2D(20, 20), WIDTH, HEIGHT);
    add(new InventoryPanel(this, new Vector2D(10, 130), inventory, 730, HEIGHT - 140));
    add(undock);
    setVisible(false);
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y, WIDTH, HEIGHT);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, position.x, position.y, WIDTH, HEIGHT, 0.9f);

    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawFillRect(gl, position.x + 10, position.y + 40, 730, 80);
    
    super.render(gl, width, height);
  }

  public void setStation(StationActor station) {
    this.station = station;
  }
  
  public StationActor getStation() {
    return this.station;
  }
}
