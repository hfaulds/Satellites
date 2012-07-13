package gui;

import gui.dialog.CreateServerDialog;
import gui.dialog.SelectServerDialog;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.client.ClientConnection;
import net.server.ServerConnection;
import scene.Scene;

@SuppressWarnings("serial")
public class PreGameGUI extends GUI {

  private static final int HEIGHT = 300;
  private static final int WIDTH = 270;
  
  private String username;

  public PreGameGUI(String username) {
    this.username = username;
    
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.setBorder(new EmptyBorder(20, 20, 20, 20));
    
    this.add(Box.createVerticalStrut(5));
    this.add(createTitleLabel());
    this.add(Box.createVerticalStrut(15));
    this.add(createJoinButton(this));
    this.add(createCreateButton(this));
    this.add(createSettingsButton());
    this.add(createExitButton());
    this.add(Box.createVerticalStrut(10));
  }
  
  private JLabel createTitleLabel() {
    final JLabel  title = new JLabel("Satellites");
    title.setFont(new Font("Helvetica", Font.BOLD, 48));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    return title;
  }

  private JButton createJoinButton(final JPanel content) {
    final JButton join = new JButton("Join Server");
    join.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scene scene = new Scene(username);
        ClientConnection connection = new ClientConnection(scene);
        if(SelectServerDialog.showDialog(content, connection)) {
          switchGUI(new InGameGUI(scene, connection));
        }
      }
    });
    join.setAlignmentX(Component.CENTER_ALIGNMENT);
    return join;
  }

  private JButton createCreateButton(final JPanel content) {
    final JButton create = new JButton("Create Server");
    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scene scene = new Scene(username);
        ServerConnection connection = new ServerConnection(scene);
        if(CreateServerDialog.showDialog(content, connection)) {
          InGameGUI g = new InGameGUI(scene, connection);
          switchGUI(g);
        }
      }
    });
    create.setAlignmentX(Component.CENTER_ALIGNMENT);
    return create;
  }

  private JButton createSettingsButton() {
    final JButton settings = new JButton("Settings");
    settings.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
      }
    });
    settings.setAlignmentX(Component.CENTER_ALIGNMENT);
    return settings;
  }

  private JButton createExitButton() {
    final JButton exit = new JButton("Exit");
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        exit();
      }
    });
    exit.setAlignmentX(Component.CENTER_ALIGNMENT);
    return exit;
  }
  
  @Override
  public int getWidth() {
    return WIDTH;
  }
  
  public int getHeight() {
    return HEIGHT;
  }
}
