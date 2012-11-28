package ingame.gimley.components.icons;

import ingame.actors.PlayerShipActor;
import ingame.gimley.WeaponIcon;

import javax.media.opengl.GL2;

import core.Weapon;
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
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.5, 0.5, 0.5, 0.9);
    Renderer2D.drawFillRect(gl, position.x, position.y, this.width, this.height);

    for(Weapon weapon : player.weapons) {
      WeaponIcon icon = weapon.getIcon();
      icon.render(gl, width, height);
    }

    super.render(gl, width, height);
  }
}
