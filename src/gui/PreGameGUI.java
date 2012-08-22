package gui;

import geometry.Vector2D;
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

import net.connections.ClientConnection;
import net.connections.ServerConnection;
import net.msg.ActorCreateMsg;
import net.msg.MsgListener;
import render.gimley.SceneWindow;
import scene.Scene;
import scene.actors.Actor;
import scene.actors.Planet1Actor;
import scene.actors.ProjectileActor;
import scene.actors.ShipActor;
import scene.actors.StationActor;
import scene.controllers.ServerActorController;
import scene.controllers.ServerShipController;

import com.esotericsoftware.kryonet.Connection;

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
        Scene scene = new Scene(username);
        ClientConnection connection = new ClientConnection(scene);
        scene.input.setConnection(connection);
        if(SelectServerDialog.showDialog(content, connection)) {
          freezeButtons();
          new SceneWindow(scene, connection);
          unfreezeButtons();
        }
      }
    });
    join.setAlignmentX(Component.CENTER_ALIGNMENT);
    return join;
  }
  
  private JButton createCreateButton(final JPanel content) {
    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final Scene scene = new Scene(username);
        ServerConnection connection = new ServerConnection(scene);
        scene.input.setConnection(connection);
        if(CreateServerDialog.showDialog(content, connection)) {
          freezeButtons();
          populateScene(scene, connection);
          new SceneWindow(scene, connection);
          unfreezeButtons();
        }
      }
    });
    create.setAlignmentX(Component.CENTER_ALIGNMENT);
    return create;
  }
  
  private void populateScene(final Scene scene, final ServerConnection server) {
    
    server.addMsgListener(new MsgListener() {
      @Override
      public void msgReceived(Object rawMsg, Connection reply) {
        ActorCreateMsg msg = (ActorCreateMsg) rawMsg;
        if(msg.actorClass.equals(ProjectileActor.class)) {
          ProjectileActor projectile = (ProjectileActor) Actor.fromInfo(msg);
          projectile.velocity._set(Vector2D.fromRotation(projectile.rotation)._mult(ProjectileActor.SPEED));
          scene.queueAddActor(projectile);
          scene.addController(new ServerActorController(projectile, server));
        }
      }

      @Override
      public Class<?> getMsgClass() {
        return ActorCreateMsg.class;
      }
    });
    
    ShipActor player = new ShipActor(0, 0);
    scene.addPlayer(player);
    scene.addController(new ServerShipController(player, server));
    
    Planet1Actor planet = new Planet1Actor(17, 17);
    scene.queueAddActor(planet);
    scene.addController(new ServerActorController(planet, server));
    
    StationActor station = new StationActor(-25, 17);
    scene.queueAddActor(station);
    scene.addController(new ServerActorController(station, server));
    
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

  private void unfreezeButtons() {
    this.join.setEnabled(true);
    this.create.setEnabled(true);
    this.settings.setEnabled(true);
    this.exit.setEnabled(true);
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
