package core.net.connections;

import ingame.actors.ProjectileActor;

public class NullConnection extends NetworkConnection {

  public NullConnection() {
    super(null, "", null);
  }

  @Override
  public void disconnect() {
  }

  @Override
  public boolean isOnline() {
    return false;
  }

  @Override
  public void sendMsg(Object msg) {
  }

  @Override
  public void fireProjectile(ProjectileActor projectile) {
  }

}
