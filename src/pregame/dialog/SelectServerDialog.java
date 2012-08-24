package pregame.dialog;


import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.InetAddress;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pregame.dialog.component.IPInputField;
import pregame.dialog.component.SelectServerListener;
import pregame.dialog.component.ServerConnector;
import pregame.dialog.component.ServerListRefresher;

import core.net.connections.ClientConnection;



@SuppressWarnings("serial")
public class SelectServerDialog extends JDialog implements ActionListener, ListSelectionListener, FocusListener {

  private static final String TITLE = "Server Selection";
  
  private static final int HEIGHT = 400;
  private static final int WIDTH = 300;

  public final JList<InetAddress> servers = new JList<InetAddress>();
  public final IPInputField ipInputField = new IPInputField();
  
  final JButton connectButton = new JButton("Connect");
  final JButton refreshButton = new JButton("Refresh");
  final JButton cancelButton = new JButton("Cancel");
  
  private final Runnable serverRefresher;
  private final Runnable serverConnector;

  private SelectServerDialog(Frame frame, ClientConnection connection) {
    super(frame, TITLE, true);
    this.serverRefresher = new ServerListRefresher(this);
    this.serverConnector = new ServerConnector(this, connection);
    this.refreshServerList();
    
    JPanel window = new JPanel();
    window.setLayout(new BoxLayout(window, BoxLayout.PAGE_AXIS));
    window.setBorder(new EmptyBorder(10, 10, 10, 10));
    window.add(Box.createVerticalStrut(10));
    window.add(createTitleLabel());
    window.add(Box.createVerticalStrut(5));
    window.add(createServerSelection());
    window.add(createButtons());
    
    this.add(window);
    this.setLocation(new Point((frame.getWidth() - WIDTH)/2, (frame.getHeight() - HEIGHT)/2));
    this.setSize(WIDTH, HEIGHT);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  private JLabel createTitleLabel() {
    final JLabel  title = new JLabel("Join Server");
    title.setFont(new Font("Helvetica", Font.BOLD, 24));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    return title;
  }

  private JPanel createServerSelection() {
    JPanel center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
    center.add(new JLabel("Server List"));
    center.add(createServersList());
    center.add(Box.createVerticalStrut(10));
    center.add(createManualInput(center));
    center.setAlignmentX(Component.CENTER_ALIGNMENT);
    return center;
  }

  private JScrollPane createServersList() {
    servers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    servers.addListSelectionListener(this);
    servers.setVisibleRowCount(5);
    JScrollPane serversPanel = new JScrollPane(servers);
    serversPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    return serversPanel;
  }

  private JPanel createManualInput(JPanel center) {
    JPanel manualJoin = new JPanel();
    manualJoin.add(new JLabel("Manual IP"));
    ipInputField.setColumns(11);
    ipInputField.addFocusListener(this);
    ipInputField.addDocumentListener(new SelectServerListener(this));
    manualJoin.add(ipInputField);
    manualJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
    return manualJoin;
  }

  private JPanel createButtons() {
    JPanel bottom = new JPanel();
    connectButton.setEnabled(false);
    connectButton.addActionListener(this);
    refreshButton.addActionListener(this);
    cancelButton.addActionListener(this);
    bottom.add(connectButton);
    bottom.add(refreshButton);
    bottom.add(cancelButton);
    bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
    return bottom;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if(source == connectButton) {
      disableButtons();
      new Thread(serverConnector).start();
      enableButtons();
    } else if(source == refreshButton) {
      refreshServerList();
    } else if(source == cancelButton) {
      this.setVisible(false);
    }
  }

  private void refreshServerList() {
    new Thread(serverRefresher).start();
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting() == false) {
      connectButton.setEnabled(!(servers.isSelectionEmpty() && ipInputField.getText().isEmpty()));
    }
  }
  
  @Override
  public void focusGained(FocusEvent e) {
    servers.clearSelection();
    connectButton.setEnabled(false);
  }

  @Override
  public void focusLost(FocusEvent e) {
    connectButton.setEnabled(!(servers.isSelectionEmpty() && ipInputField.getText().isEmpty()));
  }
  
  public void refreshConnectButton() {
    connectButton.setEnabled(ipInputField.validText(ipInputField.getText()));
  }
  
  public void displayServers(List<InetAddress> lanAddresses) {
    servers.setListData(lanAddresses.toArray(new InetAddress[0]));
  }

  public void enableButtons() {
    this.servers.setEnabled(true);
    this.ipInputField.setEnabled(true);
    this.connectButton.setEnabled(true);
    this.refreshButton.setEnabled(true);
    this.cancelButton.setEnabled(true);
  }
  
  private void disableButtons() {
    servers.setEnabled(false);
    ipInputField.setEnabled(false);
    connectButton.setEnabled(false);
    refreshButton.setEnabled(false);
    cancelButton.setEnabled(false);
  }
  
  public static boolean showDialog(Component parent, ClientConnection connection) 
  {
    Frame frame = JOptionPane.getFrameForComponent(parent);
    new SelectServerDialog(frame, connection);
    return connection.isOnline();
  }

}
