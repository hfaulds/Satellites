package scene.controllers;

import geometry.Rotation;
import geometry.Vector2D;

import java.util.List;

import scene.actors.Actor;
import scene.actors.ShipActor;


import com.esotericsoftware.kryonet.Server;


public class ServerShipController implements Controller {

  private Vector2D[] corners = new Vector2D[]{
    new Vector2D( ShipActor.WIDTH /-2, ShipActor.LENGTH / 2),
    new Vector2D( ShipActor.WIDTH / 2, ShipActor.LENGTH / 2),
    new Vector2D( ShipActor.WIDTH /-2, ShipActor.LENGTH /-2),
    new Vector2D( ShipActor. WIDTH / 2, ShipActor.LENGTH /-2)
  };
  
  public final Actor actor;
  private final Server server;
  
  public ServerShipController(Actor actor, Server server) {
    this.actor = actor;
    this.server = server;
  }

  @Override
  public void tick(List<Actor> actors) { 
    if(actor.id > -1) {
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
      actor.tick();
      server.sendToAllUDP(actor.getInfo());
    }
  }
  
  public static double calcTorque(double r, double F, double angle) {
    return r * F * Math.sin(angle) * -400;
  }

}
