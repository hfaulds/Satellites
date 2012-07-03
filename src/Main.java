import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import Scene.Scene;
import Scene.SceneRenderer;

import com.jogamp.opengl.util.FPSAnimator;


public class Main extends JFrame implements GLEventListener {
  
  private static final long serialVersionUID = 1L;
  
  private static GLCanvas canvas = new GLCanvas();
  private static FPSAnimator animator = new FPSAnimator(canvas, 60);
  
  private Scene scene = new Scene();
  private SceneRenderer renderer = new SceneRenderer(scene);
  
  public Main() {
    canvas.addGLEventListener(this);
    canvas.requestFocus();
    
    this.add(canvas);
    this.setSize(640, 480);
    this.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          animator.stop();
          dispose();
          System.exit(0);
        }
    });
    
    this.setVisible(true);
    animator.start();
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    renderer.init(drawable);
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    scene.tick();
    renderer.display(drawable);
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
