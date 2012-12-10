package ingame.controllers.client;

import ingame.actors.ProjectileActor;
import ingame.actors.player.NullPlayer;
import ingame.actors.player.PlayerShipActor;
import ingame.actors.weapons.WeaponActor;

import java.util.List;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import core.Actor;
import core.Controller;
import core.geometry.Vector2D;
import core.net.NetworkConnection;
import core.net.msg.ingame.PlayerUpdateMsg;

public class PlayerInputController implements Controller {

  private static final int FIRE_BUTTON = MouseEvent.BUTTON1;
  private static final int KEY_FORWARD = 'W';
  private static final int KEY_LEFT    = 'A';
  private static final int KEY_RIGHT   = 'D';
  
  private static final double ACCELERATION = 0.00001;
  private static final double SPIN         = 0.000005;

  private double accelMag = 0;
  private double spinMag  = 0;

  public PlayerShipActor player = new NullPlayer();
  
  private final NetworkConnection connection;
  
  public PlayerInputController(NetworkConnection connection) {
    this.connection = connection;
  }

  public void setPlayer(PlayerShipActor player) {
    this.player = player;
  }
  
  @Override
  public void tick(long dt, List<Actor> actors) {
    for(WeaponActor weapon : player.weapons) {
      weapon.tick(dt);
    }
    
    if(accelMag != 0) {
      Vector2D acceleration = new Vector2D(0, -1).rotate(player.rotation.mag)._multiply(dt * accelMag * ACCELERATION);
      player.velocity._add(acceleration);
    }
    
    if(spinMag != 0) {
      double spin = dt * spinMag * SPIN;
      player.spin._add(spin);
    }
    
    if(!(player instanceof NullPlayer)) {
      connection.sendMsg(new PlayerUpdateMsg(player.velocity, player.spin));
    }
    player.tick(dt);
  }

  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KEY_FORWARD:
        accelMag = 1;
        break;
      case KEY_LEFT:
        spinMag = 1;
        break;
      case KEY_RIGHT:
        spinMag = -1;
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KEY_FORWARD:
        accelMag = 0;
        break;
      case KEY_LEFT:
        if(spinMag == 1)
          spinMag = 0;
        break;
      case KEY_RIGHT:
        if(spinMag == -1)
          spinMag = 0;
        break;
    }
  }
  
  public void mouseMoved(Vector2D mouse, int width, int height) {
    player.setAimDirection(mouse.sub(new Vector2D(width, height).divide(2))._normalize());
  }

  public void mouseReleased(Vector2D click, MouseEvent e) {
    if(e.getButton() == PlayerInputController.FIRE_BUTTON &&
        player.getCurrentWeapon().fire()) 
    {
      Vector2D position = player.position.add(player.getAimDirection().mult(2));
      ProjectileActor projectile = new ProjectileActor(position, player.getAimDirection(), player.velocity);
      connection.fireProjectile(projectile);
    }
  }

  @Override
  public void destroy() {
    this.player = null;
  }
  
  
}
