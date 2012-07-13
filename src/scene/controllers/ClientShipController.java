package scene.controllers;

import java.util.List;

import scene.actors.Actor;

import net.msg.PlayerMsg;

import com.esotericsoftware.kryonet.Connection;

public class ClientShipController implements Controller {

  private final Actor actor;
  private final Connection connection;

  public ClientShipController(Actor actor, Connection connection) {
    this.actor = actor;
    this.connection = connection;
  }

  @Override
  public void tick(List<Actor> actors) {
    actor.tick();
    connection.sendUDP(new PlayerMsg(actor.velocity, actor.spin));
  }

}