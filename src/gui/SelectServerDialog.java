package gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.NetworkConnection;


@SuppressWarnings("serial")
public class SelectServerDialog extends JDialog implements ActionListener, ListSelectionListener, FocusListener, DocumentListener {
  
  private static final int HEIGHT = 400;
  private static final int WIDTH = 300;

  private static final String TITLE = "Server selection";

  private final RegexFormatter regexFormatter = new RegexFormatter("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
  private final JTextField serverIP = new JFormattedTextField(regexFormatter);
  private final JList<InetAddress> servers = new JList<InetAddress>();

  JButton connectButton = new JButton("Connect");
  JButton refreshButton = new JButton("Refresh");
  JButton cancelButton = new JButton("Cancel");
  
  Runnable serverRefresher = new Runnable() {
    @Override
    public void run() {
      List<InetAddress> lanAddresses = NetworkConnection.getLANAddresses();
      servers.setListData(lanAddresses.toArray(new InetAddress[0]));
    }
  };
  
  Runnable serverConnector = new Runnable() {
    @Override
    public void run() {
      
      String rawIP = serverIP.getText();
      InetAddress selectedValue = servers.getSelectedValue();
      
      if(!servers.isSelectionEmpty()){
        connect(selectedValue);
      } else if(rawIP.length() > 0) {
        try {
          connect(InetAddress.getByName(rawIP));
        } catch (UnknownHostException e1) {
          return;
        }
      }

      servers.setEnabled(true);
      serverIP.setEnabled(true);
      connectButton.setEnabled(true);
      refreshButton.setEnabled(true);
      cancelButton.setEnabled(true);
    }
  };
  
  private boolean bConnected;
  private NetworkConnection syncroniser;

  public SelectServerDialog(Frame frame, NetworkConnection syncroniser) {
    super(frame, TITLE, true);
    this.syncroniser = syncroniser;
    JPanel center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
    
    center.add(new JLabel("Server List"));
    
    servers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    servers.addListSelectionListener(this);
    servers.setVisibleRowCount(5);
    JScrollPane serversPanel = new JScrollPane(servers);
    center.add(serversPanel);
    
    JPanel manualJoin = new JPanel();
    manualJoin.add(new JLabel("Manual IP"));
    serverIP.setColumns(11);
    serverIP.addFocusListener(this);
    serverIP.getDocument().addDocumentListener(this);
    manualJoin.add(serverIP);
    center.add(manualJoin);
    
    
    JPanel bottom = new JPanel();
    connectButton.addActionListener(this);
    connectButton.setEnabled(false);
    bottom.add(connectButton);
    refreshButton.addActionListener(this);
    bottom.add(refreshButton);
    cancelButton.addActionListener(this);
    bottom.add(cancelButton);
    
    
    JPanel window = new JPanel();
    window.setLayout(new BorderLayout());
    window.setBorder(new EmptyBorder(10, 10, 10, 10) );
    window.add(center, BorderLayout.CENTER);
    window.add(bottom, BorderLayout.PAGE_END);
    
    add(window);
    setLocation(new Point((frame.getWidth() - WIDTH)/2, (frame.getHeight() - HEIGHT)/2));
    setSize(WIDTH, HEIGHT);
    new Thread(serverRefresher).start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if(source == connectButton) {
      servers.setEnabled(false);
      serverIP.setEnabled(false);
      connectButton.setEnabled(false);
      refreshButton.setEnabled(false);
      cancelButton.setEnabled(false);
      new Thread(serverConnector).start();
    } else if(source == refreshButton) {
      new Thread(serverRefresher).start();
    } else if(source == cancelButton) {
      this.setVisible(false);
    }
  }

  private void connect(InetAddress address) {
    if(address != null) {
      try {
        syncroniser.connect(address);
        bConnected = true;
        this.setVisible(false);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Connection Failed");
      }
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting() == false) {
      connectButton.setEnabled(!(servers.isSelectionEmpty() && serverIP.getText().isEmpty()));
    }
  }
  
  @Override
  public void focusGained(FocusEvent e) {
    servers.clearSelection();
    connectButton.setEnabled(false);
  }

  @Override
  public void focusLost(FocusEvent e) {
    connectButton.setEnabled(!(servers.isSelectionEmpty() && serverIP.getText().isEmpty()));
  }
  
  @Override
  public void changedUpdate(DocumentEvent arg0) {
    refreshConnectButton();
  }

  @Override
  public void insertUpdate(DocumentEvent arg0) {
    refreshConnectButton();
  }

  @Override
  public void removeUpdate(DocumentEvent arg0) {
    refreshConnectButton();
  }

  private void refreshConnectButton() {
    connectButton.setEnabled(regexFormatter.validText(serverIP.getText()));
  }
  
  public static boolean showDialog(
      Component frameComp, NetworkConnection syncroniser) 
  {
    Frame frame = JOptionPane.getFrameForComponent(frameComp);
    SelectServerDialog dialog = new SelectServerDialog(frame, syncroniser);
    dialog.setVisible(true);
    
    return dialog.bConnected;
  } 

}
