import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import Scene.Scene;
import Scene.SceneRenderer;

import com.jogamp.opengl.util.FPSAnimator;

public class Main extends JFrame implements GLEventListener {
  
  private static final long serialVersionUID = 1L;

  private final GLCanvas canvas;
  private final FPSAnimator animator;
  
  private Scene scene = new Scene();
  private SceneRenderer renderer = new SceneRenderer(scene);
  
  public Main() {

    GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
    capabilities.setSampleBuffers(true);
    capabilities.setNumSamples(4);
    canvas = new GLCanvas(capabilities);
    
    canvas.requestFocus();
    
    canvas.addGLEventListener(this);
    canvas.addMouseWheelListener(new MouseAdapter() {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {
        int wheelRotation = e.getWheelRotation();
        renderer.zoom += wheelRotation*10;
      }
    });
    
    this.add(canvas);
    this.setSize(1280 , 720);
    this.setVisible(true);
    
    animator = new FPSAnimator(canvas, 60);
    animator.start();
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    renderer.init(drawable);
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    scene.tick();
    renderer.clear(drawable);
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
