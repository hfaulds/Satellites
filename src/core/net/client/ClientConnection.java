package core.net.client;

import ingame.actors.ProjectileActor;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;

import core.Scene;
import core.net.NetworkConnection;
import core.net.client.msglisteners.ActorCreateMsgListener;
import core.net.client.msglisteners.ActorUpdateMsgListener;
import core.net.client.msglisteners.SceneCreateMsgListener;
import core.net.client.msglisteners.ShipDockMsgListener;
import core.net.msg.MsgListener;
import core.net.msg.pregame.LoginMsg;

public class ClientConnection extends NetworkConnection {

  private final Client client = new Client();
  private final ClientListener listener;
  
  public ClientConnection(Scene scene, LoginMsg login) {
    super(scene, login.username);
    this.listener = new ClientListener(scene, login);
  }

  public void connect(InetAddress address) throws IOException {
    registerMsgClasses(client);
    setupMsgListeners(scene);
    client.addListener(listener);
    client.start();
    client.connect(TIMEOUT, address, TCP_PORT, UDP_PORT);
    super.setAddress(address);
  }
  
  public void addMsgListener(MsgListener msgListener) {
    this.listener.addMsgListener(msgListener);
  }

  private void setupMsgListeners(final Scene scene) {
    this.addMsgListener(new SceneCreateMsgListener(scene));
    this.addMsgListener(new ActorUpdateMsgListener(scene));
    this.addMsgListener(new ActorCreateMsgListener(scene));
    this.addMsgListener(new ShipDockMsgListener(scene));
  }

  
  @Override
  public void disconnect() {
    client.stop();
  }

  @Override
  public boolean isOnline() {
    return client.isConnected();
  }

  @Override
  public void sendMsg(Object msg) {
    if(client.isConnected()) {
      client.sendUDP(msg);
    }
  }

  @Override
  public void fireProjectile(ProjectileActor projectile) {
    super.addActor(projectile);
  }
}
