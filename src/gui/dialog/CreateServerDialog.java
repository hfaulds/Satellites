package gui.dialog;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.server.ServerConnection;

@SuppressWarnings("serial")
public class CreateServerDialog extends JDialog {

  private static final int HEIGHT = 150;
  private static final int WIDTH = 300;
  
  private static final String TITLE = "Server Creation";
  private final ServerConnection connection;

  public CreateServerDialog(Frame frame, ServerConnection connection) {
    super(frame, TITLE, true);
    this.connection = connection;
    this.add(createContent());
    this.setLocation(new Point((frame.getWidth() - WIDTH)/2, (frame.getHeight() - HEIGHT)/2));
    this.setSize(WIDTH, HEIGHT);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  private JPanel createContent() {
    JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
    content.add(Box.createVerticalStrut(5));
    content.add(createTitleLabel());
    content.add(Box.createVerticalStrut(5));
    content.add(createLabel("Server Name"));
    content.add(createTextField());
    content.add(createButtons());
    return content;
  }

  private JLabel createTitleLabel() {
    final JLabel  title = new JLabel("Create Server");
    title.setFont(new Font("Helvetica", Font.BOLD, 24));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    return title;
  }

  private JLabel createLabel(String name) {
    JLabel label = new JLabel(name);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    return label;
  }

  private JTextField createTextField() {
    JTextField textField = new JTextField(8);
    textField.setAlignmentX(Component.CENTER_ALIGNMENT);
    return textField;
  }

  private JPanel createButtons() {
    JPanel buttons = new JPanel();
    buttons.add(createCreateButton(this));
    buttons.add(createCancelButton());
    return buttons;
  }

  private JButton createCancelButton() {
    JButton cancel = new JButton("Cancel");
    cancel.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
    return cancel;
  }

  private JButton createCreateButton(final Component parent) {
    JButton create = new JButton("Create");
    create.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        if(connection.create()) {
          setVisible(false);
        } else {
          JOptionPane.showMessageDialog(parent, "Creation Failed");
        }
      }
    });
    return create;
  }

  public static boolean showDialog(Component parent, ServerConnection connection) 
  {
    Frame frame = JOptionPane.getFrameForComponent(parent);
    new CreateServerDialog(frame, connection);
    return connection.isOnline();
  }
}
