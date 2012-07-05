package Controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Actors.Actor;
import Actors.PlayerActor;
import Math.Vector2D;
import Scene.SceneRenderer;

public class PlayerController extends Controller<PlayerActor> implements MouseListener {

  public static final double MOUSE_FORCE = 1e-7;
  
  public boolean mouseDown      = false;
  public Vector2D mousePosition = new Vector2D();
  
  public PlayerController(PlayerActor actor) {
    super(actor);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    
    if(e.getButton() == MouseEvent.BUTTON1)
    {
      int height = e.getComponent().getHeight();
      mousePosition.x = e.getX();
      mousePosition.y = (height -  e.getY());
      mouseDown = true;
    }
  }
  
  @Override
  public void mouseReleased(MouseEvent e) {
    mouseDown = false;
  }

  @Override
  public void tick(Actor[] actors) {    
    if(mouseDown) {
      //TODO change to take into account actual screen position with a maximum force and apply torque
      Vector2D coords = SceneRenderer.project(actor.position);
      Vector2D force  = coords.sub(mousePosition)._normalize()._mult(MOUSE_FORCE);
      actor.applyForce(force);
    }
  }
  
  @Override
  public void mouseClicked(MouseEvent arg0) {}
  @Override
  public void mouseEntered(MouseEvent arg0) {}
  @Override
  public void mouseExited(MouseEvent arg0) {}

}
