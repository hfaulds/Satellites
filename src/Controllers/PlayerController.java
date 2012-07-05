package Controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Actors.Actor;
import Actors.PlayerActor;
import Math.Vector2D;
import Scene.SceneRenderer;

public class PlayerController extends Controller<PlayerActor> implements MouseListener {

  public boolean mouseDown      = false;
  public Vector2D mousePosition = new Vector2D();
  
  public PlayerController(PlayerActor actor) {
    super(actor);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    
    if(e.getButton() == MouseEvent.BUTTON1)
    {
      double[] player = SceneRenderer.project(actor.position);
  
      int height = e.getComponent().getHeight();
      mousePosition.x =           e.getX() - player[0];
      mousePosition.y = height -  e.getY() - player[1];
      mouseDown = true;
    }
  }
  
  @Override
  public void mouseReleased(MouseEvent e) {
    mouseDown = false;
  }

  @Override
  public void tick(Actor[] actors) {    if(mouseDown)
    actor.applyForce(mousePosition._normalize()._mult(0.0000001));
  }
  
  @Override
  public void mouseClicked(MouseEvent arg0) {}
  @Override
  public void mouseEntered(MouseEvent arg0) {}
  @Override
  public void mouseExited(MouseEvent arg0) {}

}
