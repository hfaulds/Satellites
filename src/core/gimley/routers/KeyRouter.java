package core.gimley.routers;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import core.gimley.components.GComponent;

public class KeyRouter implements KeyListener {

  private final GComponent root;

  public KeyRouter(GComponent root) {
    this.root = root;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    GComponent focus = root.subcomponents.getFocus();
    focus.keyReleased(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    GComponent focus = root.subcomponents.getFocus();
    focus.keyPressed(e);
  }

  @Override
  public void keyTyped(KeyEvent e) {}
}