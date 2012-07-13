package gui;

import gui.components.SelectServerDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.client.ClientConnection;
import net.server.ServerConnection;
import scene.Scene;

@SuppressWarnings("serial")
public class PreGameGUI extends GUI {

  public PreGameGUI() {
    add(createTopBar());
  }

  private JPanel createTopBar() {
    final JPanel topBar = new JPanel();
    
    final JButton create = new JButton("create server");
    final JButton join = new JButton("join server");
    
    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scene scene = new Scene();
        ServerConnection connection = new ServerConnection(scene);
        if(connection.create()) {
          fireEvent(new GUIEvent(new InGameGUI(scene, connection)));
        }
      }
    });
    
    join.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scene scene = new Scene();
        ClientConnection connection = new ClientConnection(scene);
        if(SelectServerDialog.showDialog(topBar, connection)) {
          fireEvent(new GUIEvent(new InGameGUI(scene, connection)));
        }
      }
    });
    
    topBar.add(create);
    topBar.add(join);
    return topBar;
  }

  @Override
  public void close() {
    
  }
}
