
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.NetworkConnection;

import scene.Camera;
import scene.Scene;
import scene.SceneUpdater;

import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class Main extends JFrame implements GLEventListener {
  
  private Scene scene = new Scene();
  private Camera renderer = new Camera(scene);
  private SceneUpdater updater = new SceneUpdater(scene);
  private NetworkConnection syncroniser = new NetworkConnection(scene);
  
  private final GLCanvas canvas = createCanvas(createCapabilities());
  private final FPSAnimator animator = new FPSAnimator(canvas, 20);
  
  public Main() {
    setupFrame();
    animator.start();
    canvas.requestFocus();
  }

  private void setupFrame() {
    this.add(createContent());
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        syncroniser.disconnect();
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
    return window;
  }

  private JPanel createTopBar() {
    JPanel topBar = new JPanel();
    
    
    final JButton create = new JButton("create server");
    final JButton join = new JButton("join server");
    final JLabel label = new JLabel("offline");
    
    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!syncroniser.isOnline()) {
          if(syncroniser.createServer()) {
            create.setText("close");
            label.setText(syncroniser.getAddress().toString());
            join.setVisible(false);
          }
        } else {
          syncroniser.disconnect();
          create.setText("create server");
          label.setText("offline");
          join.setVisible(true);
        }
      }
    });
    
    join.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!syncroniser.isOnline()) {
          if(syncroniser.createClient()) {
            join.setText("disconnect");
            label.setText(syncroniser.getAddress().toString());
            create.setVisible(false);
          }
        } else {
          syncroniser.disconnect();
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

  private GLCanvas createCanvas(GLCapabilities capabilities) {
    GLCanvas canvas = new GLCanvas(capabilities);
    canvas.addGLEventListener(this);
    canvas.addMouseListener(renderer);
    canvas.addMouseWheelListener(renderer);
    canvas.addMouseMotionListener(renderer);
    canvas.addKeyListener(scene.playerController);
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
    renderer.reshape(drawable, x, y, width, height);
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {}
  
  public static void main(String[] args) {
    new Main();
  }
}
