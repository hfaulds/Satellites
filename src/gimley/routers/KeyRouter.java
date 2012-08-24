package gimley.routers;

import gimley.core.components.GComponent;
import scene.Scene;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyRouter implements KeyListener {

  private final GComponent root;
  private final Scene scene;

  public KeyRouter(GComponent root, Scene scene) {
    this.root = root;
    this.scene = scene;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    GComponent focus = root.subcomponents.getFocus();
    if(focus != root) {
      focus.keyReleased(e);
    } else {
      scene.input.keyReleased(e);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    GComponent focus = root.subcomponents.getFocus();
    if(focus != root) {
      focus.keyPressed(e);
    } else {
      scene.input.keyPressed(e);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {}
}