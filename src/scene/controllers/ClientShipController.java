package scene.controllers;

import java.util.List;

import scene.actors.Actor;

import net.msg.PlayerUpdateMsg;

import com.esotericsoftware.kryonet.Connection;

public class ClientShipController implements Controller {

  private final Actor actor;
  private final Connection connection;
  
  public ClientShipController(Actor actor, Connection connection) {
    this.actor = actor;
    this.connection = connection;
  }

  @Override
  public void tick(long dt, List<Actor> actors) {
    connection.sendUDP(new PlayerUpdateMsg(actor.velocity, actor.spin));
  }

}