package render.gimley;

import render.gimley.components.GComponent;
import scene.Scene;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyRouter implements KeyListener {

  private final GComponent parent;
  private final Scene scene;

  KeyRouter(GComponent parent, Scene scene) {
    this.parent = parent;
    this.scene = scene;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    GComponent focus = parent.subcomponents.getFocus();
    if(focus != parent) {
      focus.keyReleased(e);
    } else {
      scene.input.keyReleased(e);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    GComponent focus = parent.subcomponents.getFocus();
    if(focus != parent) {
      focus.keyPressed(e);
    } else {
      scene.input.keyPressed(e);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {}
}