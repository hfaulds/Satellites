package pregame.gui;


import ingame.actors.player.PlayerShipActor;
import ingame.controllers.PlayerInputController;
import ingame.gimley.SceneWindow;

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

import pregame.dialog.SelectServerDialog;
import core.Scene;
import core.ScenePlayerListener;
import core.db.entities.UserEntity;
import core.net.client.ClientConnection;


@SuppressWarnings("serial")
public class PreGameGUI extends GUI {

  private static final int HEIGHT = 300;
  private static final int WIDTH = 270;

  final JButton join = new JButton("Join Server");
  final JButton create = new JButton("Create Server");
  final JButton settings = new JButton("Settings");
  final JButton exit = new JButton("Exit");
  
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
    join.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
        final Scene scene = new Scene();
        final ClientConnection connection = new ClientConnection(scene, new UserEntity(username, "test"));
        final PlayerInputController input = new PlayerInputController(connection);
        
        scene.addController(input);
        scene.addNewPlayerListener(new ScenePlayerListener() {
          @Override
          public void playerActorChanged(PlayerShipActor player) {
            SceneWindow window = new SceneWindow(scene, input, player, connection);
            window.setVisible(true);
            input.setPlayer(player);
          }
        });
        
        if(SelectServerDialog.showDialog(content, connection)) {
          freezeButtons();
        }
      }

    });
    join.setAlignmentX(Component.CENTER_ALIGNMENT);
    return join;
  }
  
  private JButton createCreateButton(final JPanel content) {
    create.setAlignmentX(Component.CENTER_ALIGNMENT);
    return create;
  }

  private JButton createSettingsButton() {
    settings.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
      }
    });
    settings.setAlignmentX(Component.CENTER_ALIGNMENT);
    return settings;
  }

  private JButton createExitButton() {
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        exit();
      }
    });
    exit.setAlignmentX(Component.CENTER_ALIGNMENT);
    return exit;
  }

  private void freezeButtons() {
    this.join.setEnabled(false);
    this.create.setEnabled(false);
    this.settings.setEnabled(false);
    this.exit.setEnabled(false);
  }
  
  @Override
  public int getWidth() {
    return WIDTH;
  }
  
  public int getHeight() {
    return HEIGHT;
  }
}
