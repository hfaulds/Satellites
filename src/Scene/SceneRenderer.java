package Scene;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import Renderers.Renderer2D;
import Renderers.Renderer3D;

public class SceneRenderer implements GLEventListener, MouseWheelListener {

  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 20;
  
  private final Renderer3D renderer3D = new Renderer3D();
  private final Renderer2D renderer2D = new Renderer2D();

  private final Scene scene;
  public double zoom = ZOOM_DEFAULT;
  
  public SceneRenderer(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    renderer3D.init(glFromDrawable(drawable));
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    final GL2 gl = drawable.getGL().getGL2();
    
    double width = drawable.getWidth();
    double height = drawable.getHeight();
    
    renderer3D.clear(gl);
    renderer3D.preRender(gl, width/height, zoom);
    renderer3D.render(gl, scene.actors);
    renderer2D.preRender(gl, width, height);
    renderer2D.render(gl, scene.ui);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    renderer3D.reshape(glFromDrawable(drawable), x, y, width, height);
  }
  
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    this.zoom += e.getPreciseWheelRotation() * ZOOM_RATE;
    renderer3D.updateMatrices();
  }

  private GL2 glFromDrawable(GLAutoDrawable drawable) {
    return drawable.getGL().getGL2();
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {}
}
