package scene.controllers;

import java.util.List;



import com.esotericsoftware.kryonet.Connection;

import core.Actor;
import core.net.msg.PlayerUpdateMsg;

public class ClientShipController implements Controller {

  private Actor actor;
  private final Connection connection;
  
  public ClientShipController(Actor actor, Connection connection) {
    this.actor = actor;
    this.connection = connection;
  }

  @Override
  public void tick(long dt, List<Actor> actors) {
    connection.sendUDP(new PlayerUpdateMsg(actor.velocity, actor.spin));
  }

  @Override
  public void destroy() {
    this.actor = null;
  }

}