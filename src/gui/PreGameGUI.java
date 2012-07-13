package gui;

import gui.components.CreateServerDialog;
import gui.components.SelectServerDialog;
import gui.event.GUIEvent;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.client.ClientConnection;
import net.server.ServerConnection;
import scene.Scene;

@SuppressWarnings("serial")
public class PreGameGUI extends GUI {

  private static final int HEIGHT = 500;
  private static final int WIDTH = 250;

  public PreGameGUI() {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    
    this.add(Box.createVerticalStrut(25));
    this.add(createTitleLabel());
    this.add(Box.createVerticalStrut(15));
    this.add(createJoinButton(this));
    this.add(createCreateButton(this));
    this.add(createSettingsButton());
    this.add(createExitButton());
  }

  private JButton createJoinButton(final JPanel content) {
    final JButton join = new JButton("join server");
    join.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scene scene = new Scene();
        ClientConnection connection = new ClientConnection(scene);
        if(SelectServerDialog.showDialog(content, connection)) {
          switchGUI(new GUIEvent(new InGameGUI(scene, connection)));
        }
      }
    });
    join.setAlignmentX(Component.CENTER_ALIGNMENT);
    return join;
  }

  private JLabel createTitleLabel() {
    final JLabel  title = new JLabel("Satellites");
    title.setFont(new Font("Helvetica", Font.BOLD, 48));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    return title;
  }

  private JButton createCreateButton(final JPanel content) {
    final JButton create = new JButton("create server");
    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scene scene = new Scene();
        ServerConnection connection = new ServerConnection(scene);
        if(CreateServerDialog.showDialog(content, connection)) {
          switchGUI(new GUIEvent(new InGameGUI(scene, connection)));
        }
      }
    });
    create.setAlignmentX(Component.CENTER_ALIGNMENT);
    return create;
  }

  private JButton createSettingsButton() {
    final JButton settings = new JButton("settings");
    settings.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
      }
    });
    settings.setAlignmentX(Component.CENTER_ALIGNMENT);
    return settings;
  }

  private JButton createExitButton() {
    final JButton exit = new JButton("exit");
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
