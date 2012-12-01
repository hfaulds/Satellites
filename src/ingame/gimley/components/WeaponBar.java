package ingame.gimley.components;

import ingame.actors.PlayerShipActor;
import ingame.actors.weapons.Weapon;
import ingame.gimley.components.icons.WeaponIcon;

import javax.media.opengl.GL2;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;

public class WeaponBar extends GComponent {

  private static final int WIDTH = 300;
  private static final int HEIGHT = 40;
  
  private final PlayerShipActor player;

  public WeaponBar(GComponent parent, PlayerShipActor player) {
    super(parent, new Vector2D(parent.width/2 - WIDTH/2, parent.height - HEIGHT), WIDTH, HEIGHT);
    this.player = player;
  }
  
  @Override
  public void init(GL2 gl, int width, int height) {
    for(Weapon weapon : player.weapons) {
      add(new WeaponIcon(this, weapon));
    }
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.5, 0.5, 0.5, 0.3);
    Renderer2D.drawFillRect(gl, position.x, position.y, this.width, this.height);

    super.render(gl, width, height);
  }
}
