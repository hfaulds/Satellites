import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import Scene.Scene;
import Scene.Camera;
import Scene.SceneUpdater;

import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class Main extends JFrame implements GLEventListener {

  private final GLCanvas canvas;
  private final FPSAnimator animator;
  
  private Scene scene = new Scene();
  private SceneUpdater updater = new SceneUpdater(scene);
  private Camera renderer = new Camera(scene);

  public Main() {
    GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
    capabilities.setSampleBuffers(true);
    capabilities.setNumSamples(4);
    
    canvas = new GLCanvas(capabilities);
    canvas.addGLEventListener(this);
    canvas.addMouseListener(renderer);
    canvas.addMouseWheelListener(renderer);
    canvas.addMouseMotionListener(renderer);
    canvas.addKeyListener(scene.playerController);
    
    this.add(canvas);
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        dispose();
        System.exit(0);
      }
    });

    this.setSize(1280 , 720);
    this.setVisible(true);
    
    animator = new FPSAnimator(canvas, 60);
    animator.start();
    canvas.requestFocus();
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
