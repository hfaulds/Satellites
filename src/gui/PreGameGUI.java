package gui;

import gui.components.SelectServerDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.client.ClientConnection;
import net.server.ServerConnection;
import scene.Scene;

@SuppressWarnings("serial")
public class PreGameGUI extends GUI {

  public PreGameGUI() {
    final JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
    
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
        if(SelectServerDialog.showDialog(content, connection)) {
          fireEvent(new GUIEvent(new InGameGUI(scene, connection)));
        }
      }
    });
    
    content.add(create);
    content.add(join);
    this.add(content);
  }
  
  @Override
  public int getWidth() {
    return 250;
  }
  
  public int getHeight() {
    return 500;
  }
}
