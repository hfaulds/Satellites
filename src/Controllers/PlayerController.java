package Controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Actors.Actor;
import Graphics.Ship.ShipControlSprite;
import Math.Vector2D;
import Renderers.Renderer3D;

public class PlayerController extends Controller implements MouseListener {

  public static final double MOUSE_FORCE = 1e-8;
  
  public boolean bMouseMove     = false;
  public Vector2D mousePosition = new Vector2D();
  
  public PlayerController(Actor actor) {
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
      Vector2D playerCoord  = Renderer3D.project(actor.position);
      Vector2D controlCoord = Renderer3D.project(actor.position.add(new Vector2D(ShipControlSprite.CONTROL_RADIUS, 0)));
      Vector2D distToMouse  = playerCoord.sub(mousePosition);
      Vector2D distToEdge   = playerCoord.sub(controlCoord);
      
      if(distToMouse.magnitude() > distToEdge.magnitude())
        return;
      
      distToMouse.y *= -1;
      actor.applyForce(distToMouse._normalize()._mult(MOUSE_FORCE));
    }
  }
  
  @Override
  public void mouseClicked(MouseEvent arg0) {}
  @Override
  public void mouseEntered(MouseEvent arg0) {}
  @Override
  public void mouseExited(MouseEvent arg0) {}

}
