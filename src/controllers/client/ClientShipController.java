package controllers.client;

import java.util.List;

import net.PlayerInfo;

import com.esotericsoftware.kryonet.Connection;

import controllers.Controller;

import actors.Actor;
import actors.ShipActor;

public class ClientShipController implements Controller {

  private final ShipActor actor;
  private final Connection connection;

  public ClientShipController(ShipActor actor, Connection connection) {
    this.actor = actor;
    this.connection = connection;
  }

  @Override
  public void tick(List<Actor> actors) {
    actor.tick();
    connection.sendUDP(new PlayerInfo(actor.velocity, actor.spin));
  }

}