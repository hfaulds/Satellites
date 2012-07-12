
import gui.SelectServerDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.NetworkConnection;
import net.NoConnection;
import net.client.ClientConnection;
import net.msg.ChatMsg;
import net.server.ServerConnection;
import scene.Camera;
import scene.Scene;
import scene.SceneUpdater;

import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class Satellites extends JFrame implements GLEventListener {
  
  private Scene scene = new Scene();
  
  private Camera renderer = new Camera(scene);
  private SceneUpdater updater = new SceneUpdater(scene);
  private NetworkConnection connection = new NoConnection();
  
  private GLWindow glWindow = createGLWindow(createCapabilities());
  private final NewtCanvasAWT canvas = createCanvas(glWindow);
  private final FPSAnimator animator = new FPSAnimator(glWindow, 20);

  public Satellites() {
    setupFrame();
    animator.start();
  }

  private void setupFrame() {
    this.add(createContent());
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        connection.disconnect();
        dispose();
        System.exit(0);
      }
    });
    this.setSize(1280 , 720);
    this.setVisible(true);
  }
  
  private JPanel createContent() {
    JPanel window = new JPanel();
    window.setLayout(new BorderLayout());
    window.add(createTopBar(), BorderLayout.PAGE_START);
    window.add(canvas, BorderLayout.CENTER);
    window.add(createBottomBar(), BorderLayout.PAGE_END);
    glWindow.requestFocus();
    return window;
  }

  private JPanel createBottomBar() {
    final JPanel bottomBar = new JPanel(new BorderLayout());
    final JPanel chatBar = new JPanel();
    
    final JTextField message = new JTextField(25);
    final JButton send = new JButton("send");
    
    glWindow.addKeyListener(new KeyListener() {
      @Override
      public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(bottomBar.isVisible()) {
          switch(keyCode) {
            case 10: // enter
              send.doClick();
              break;
            case 27: // escape
              bottomBar.setVisible(false);
              break;
            case 8: // backspace
              String currentText = message.getText();
              int length = currentText.length();
              if(length > 0)
                message.setText(currentText.substring(0, length - 1));
              break;
            default:
              char character = (char)keyCode;
              if(!e.isShiftDown())
                character =  Character.toLowerCase(character);
              message.setText(message.getText() + character);
          }
        } else {
          if(keyCode == 'T') {
            bottomBar.setVisible(true);
          }
        }
      }

      public void keyPressed(KeyEvent e) {}
      public void keyTyped(KeyEvent e) {}
    });
    
    send.setFocusable(false);
    send.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String text = message.getText();
        if(!text.equals("")) {
          scene.messageHandler.addMessage(new ChatMsg(message.getText()));
          connection.sendMessage(new ChatMsg(message.getText()));
          message.setText("");
        }
      }
    });
    chatBar.add(message);
    chatBar.add(send);
    
    bottomBar.add(chatBar, BorderLayout.WEST);
    bottomBar.setVisible(false);
    return bottomBar;
  }

  private JPanel createTopBar() {
    final JPanel topBar = new JPanel();
    
    final JButton create = new JButton("create server");
    final JButton join = new JButton("join server");
    final JLabel label = new JLabel("offline");
    
    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!connection.isOnline()) {
          ServerConnection serverCon = new ServerConnection(scene);
          if(serverCon.create()) {
            create.setText("close");
            label.setText(serverCon.getAddress().toString());
            join.setVisible(false);
            connection = serverCon;
          }
        } else {
          connection.disconnect();
          create.setText("create server");
          label.setText("offline");
          join.setVisible(true);
        }
      }
    });
    
    join.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!connection.isOnline()) {
          ClientConnection clientCon = new ClientConnection(scene);
          if(SelectServerDialog.showDialog(topBar, clientCon)) {
            join.setText("disconnect");
            label.setText(clientCon.getAddress().toString());
            create.setVisible(false);
            connection = clientCon;
          }
        } else {
          connection.disconnect();
          join.setText("join server");
          label.setText("offline");
          create.setVisible(true);
        }
      }
    });
    
    topBar.add(create);
    topBar.add(join);
    topBar.add(label);
    return topBar;
  }

  private GLCapabilities createCapabilities() {
    GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
    capabilities.setSampleBuffers(true);
    capabilities.setNumSamples(4);
    return capabilities;
  }

  private GLWindow createGLWindow(GLCapabilities capabilities) {
    GLWindow window = GLWindow.create(capabilities);
    window.addGLEventListener(this);
    window.addKeyListener(scene.input);
    return window;
  }

  private NewtCanvasAWT createCanvas(GLWindow window) {
    NewtCanvasAWT canvas = new NewtCanvasAWT(window);
    canvas.addMouseListener(renderer);
    canvas.addMouseWheelListener(renderer);
    canvas.addMouseMotionListener(renderer);
    return canvas;
  }


  @Override
  public void init(GLAutoDrawable drawable) {
    renderer.init(drawable);
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    updater.tick();
    renderer.render(drawable);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    updater.tick();
    renderer.render(drawable);
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {}
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Satellites();
      }
    });
  }
}
