package pregameGUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class LoginGUI extends GUI {
  
  private static final int HEIGHT = 230;
  private static final int WIDTH = 270;

  private JTextField userNameInput = new JTextField(5);
  
  public LoginGUI() {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.setBorder(new EmptyBorder(20, 20, 20, 20));
    
    this.add(Box.createVerticalStrut(15));
    this.add(createTitleLabel());
    this.add(Box.createVerticalStrut(5));
    this.add(createNameLabel());
    this.add(userNameInput);
    this.add(Box.createVerticalStrut(5));
    this.add(createLoginButton(this));
    this.add(Box.createVerticalStrut(25));
  }
  
  private JButton createLoginButton(final JPanel content) {
    final JButton join = new JButton("Login");
    join.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switchGUI(new PreGameGUI(userNameInput.getText()));
      }
    });
    join.setAlignmentX(Component.CENTER_ALIGNMENT);
    return join;
  }

  private JLabel createNameLabel() {
    JLabel label = new JLabel("Player Name");
    label.setFont(new Font("Helvetica", Font.BOLD, 12));
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    return label;
  }

  private JLabel createTitleLabel() {
    final JLabel  title = new JLabel("Login");
    title.setFont(new Font("Helvetica", Font.BOLD, 30));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    return title;
  }
  
  @Override
  public int getHeight() {
    return HEIGHT;
  }
  
  @Override
  public int getWidth() {
    return WIDTH;
  }
}
