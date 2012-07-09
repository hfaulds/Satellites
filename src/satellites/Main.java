package satellites;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import scene.Camera;
import scene.Scene;
import scene.SceneUpdater;


import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class Main extends JFrame implements GLEventListener {

  private final GLCanvas canvas = createCanvas(createCapabilities());
  private final FPSAnimator animator = new FPSAnimator(canvas, 60);
  
  private Scene scene = new Scene();
  private Camera renderer = new Camera(scene);
  private SceneUpdater updater = new SceneUpdater(scene);

  private SatellitesServerManager serverManager = new SatellitesServerManager();
  
  public Main() {
    setupFrame();
    animator.start();
    canvas.requestFocus();
  }

  private JPanel createContent() {
    JButton button = createConnectButton();
    
    JPanel window = new JPanel();
    window.setLayout(new BoxLayout(window, BoxLayout.PAGE_AXIS));
    window.add(button);
    window.add(canvas);
    
    return window;
  }

  private JButton createConnectButton() {
    JButton button = new JButton("start server");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        serverManager.connectOrHost();
      }
    });
    return button;
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

  private void setupFrame() {
    this.add(createContent());
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        serverManager.cleanup();
        dispose();
        System.exit(0);
      }
    });
    this.setSize(1280 , 720);
    this.setVisible(true);
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
