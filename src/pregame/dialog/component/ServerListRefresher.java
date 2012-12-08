package pregame.dialog.component;


import java.net.InetAddress;
import java.util.List;

import pregame.dialog.SelectServerDialog;

import com.esotericsoftware.kryonet.Client;

import core.net.NetworkConnection;

public class ServerListRefresher implements Runnable {
  
  private final SelectServerDialog selectServerDialog;

  public ServerListRefresher(SelectServerDialog selectServerDialog) {
    this.selectServerDialog = selectServerDialog;
  }

  @Override
  public void run() {
    List<InetAddress> lanAddresses = new Client().discoverHosts(NetworkConnection.UDP_PORT, NetworkConnection.TIMEOUT);
    this.selectServerDialog.displayServers(lanAddresses);
  }
}