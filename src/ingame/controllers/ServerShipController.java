package ingame.controllers;

import ingame.actors.ShipActor;

import java.util.List;

import core.Actor;
import core.Controller;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.net.connections.ServerConnection;

public class ServerShipController implements Controller {

  // TODO : Replace using bounding box
  private Vector2D[] corners = new Vector2D[]{
    new Vector2D( ShipActor.WIDTH /-2, ShipActor.LENGTH / 2),
    new Vector2D( ShipActor.WIDTH / 2, ShipActor.LENGTH / 2),
    new Vector2D( ShipActor.WIDTH /-2, ShipActor.LENGTH /-2),
    new Vector2D( ShipActor. WIDTH / 2, ShipActor.LENGTH /-2)
  };
  
  public Actor actor;
  private final ServerConnection connection;
  
  public ServerShipController(Actor actor, ServerConnection connection) {
    this.actor = actor;
    this.connection = connection;
  }

  @Override
  public void tick(long dt, List<Actor> actors) {
    Vector2D   force            = new Vector2D();
    Vector2D[] cornerForces     = new Vector2D[corners.length];
    Vector2D[] cornerOffsets    = new Vector2D[corners.length];
    
    for(int i=0; i < cornerForces.length; i++) {
      cornerForces[i]  = new Vector2D();
      cornerOffsets[i] = corners[i]._rotate(actor.rotation.mag);
    }
    
    for(Actor other : actors) {
      if(other != actor) {
        force._add(actor.gravForceFrom(other));
        
        for(int i=0; i < corners.length; i++) {
          cornerForces[i]._add(actor.gravForceFrom(other, cornerOffsets[i])); 
        }
      }
    }

    actor.applyForce(force);
    
    Rotation torque = new Rotation();

    for(int i=0; i < cornerForces.length; i++)
    {
      Vector2D F        = cornerForces[i];
      Vector2D offset   = cornerOffsets[i];
      double r          = offset.magnitude();
      double angle      = Vector2D.angle(F, offset);
      
      double moment     = calcTorque(r, F.magnitude(), angle);
      
      torque._add(moment);
    }

    actor.applyTorque(torque);
    actor.tick(dt);
    connection.sendMsg(actor.getUpdateMsg());
  }
  
  public static double calcTorque(double r, double F, double angle) {
    return r * F * Math.sin(angle) * -400;
  }

  @Override
  public void destroy() {
    this.actor = null;
  }
}
