package pregameGUI.dialog.component;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import pregameGUI.dialog.SelectServerDialog;

import core.net.connections.ClientConnection;


public class ServerConnector implements Runnable {
  
  private final SelectServerDialog dialog;
  private final ClientConnection connection;
  
  public ServerConnector(SelectServerDialog dialog, ClientConnection connection) {
    this.dialog = dialog;
    this.connection = connection;
  }

  @Override
  public void run() {
    String rawIP = dialog.ipInputField.getText();
    InetAddress selectedValue = dialog.servers.getSelectedValue();
    
    if(!dialog.servers.isSelectionEmpty()){
      attempConnection(selectedValue, connection);
    } else if(rawIP.length() > 0) {
      try {
        attempConnection(InetAddress.getByName(rawIP), connection);
      } catch (UnknownHostException e1) {
        return;
      }
    }
    
  }

  public void attempConnection(InetAddress address, ClientConnection connection) {
    if(address != null) {
      try {
        connection.connect(address);
        dialog.setVisible(false);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(dialog, "Connection Failed");
      }
    }
  }
}