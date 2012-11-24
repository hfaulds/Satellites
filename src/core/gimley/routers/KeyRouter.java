package core.gimley.routers;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import core.gimley.components.GComponent;

public class KeyRouter implements KeyListener {

  private final GComponent component;

  public KeyRouter(GComponent component) {
    this.component = component;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    component.keyReleased(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    component.keyPressed(e);
  }

  @Override
  public void keyTyped(KeyEvent e) {}
}