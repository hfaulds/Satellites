package Controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Actors.Actor;
import Actors.PlayerActor;
import Graphics.UI.ShipControlCircle;
import Math.Vector2D;
import Renderers.Renderer3D;

public class PlayerController extends Controller<PlayerActor> implements MouseListener {

  public static final double MOUSE_FORCE = 1e-8;
  
  public boolean bMouseMove     = false;
  public Vector2D mousePosition = new Vector2D();
  
  public PlayerController(PlayerActor actor) {
    super(actor);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if(e.getButton() == MouseEvent.BUTTON1)
    {
      mousePosition.x = e.getX();
      mousePosition.y = e.getY();
      bMouseMove = true;
    }
  }
  
  @Override
  public void mouseReleased(MouseEvent e) {
    bMouseMove = false;
  }

  @Override
  public void tick(Actor[] actors) {    
    if(bMouseMove) {
      Vector2D coords = Renderer3D.project(actor.position);
      Vector2D direction = coords.sub(mousePosition);
      direction.y *= -1;
      
      double magnitude = direction.magnitude();
      
      if(magnitude > ShipControlCircle.CONTROL_RADIUS)
        return;
      
      Vector2D force  = direction._normalize()._mult(MOUSE_FORCE);
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
