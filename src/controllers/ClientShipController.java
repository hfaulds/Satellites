package controllers;

import java.util.List;

import net.msg.PlayerInfo;
import actors.Actor;

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
    connection.sendUDP(new PlayerInfo(actor.velocity, actor.spin));
  }

}