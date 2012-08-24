package gimley.core;

import gimley.core.components.GComponent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

import core.render.Renderer2D;

public class GFrame extends GComponent implements GLEventListener {
  
  private final GLWindow window = GLWindow.create(new GLCapabilities(GLProfile.getDefault()));
  private final Renderer2D renderer2D = new Renderer2D();
  private final FPSAnimator animator = new FPSAnimator(window, 80);

  public GFrame(GComponent parent, int width, int height) {
    super(parent);
    
    this.width = 800;
    this.height = 800;
    
    window.setSize(width, height);
    window.addGLEventListener(this);
  }

  public void destroy() {
    window.destroy();
    setVisible(false);
  }
  
  /* Rendering */

  @Override
  public void setVisible(boolean visible) {
    if(visible) {
      animator.start();
    } else {
      animator.stop();
    }
    window.setVisible(visible);
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    int width = drawable.getWidth();
    int height = drawable.getHeight();
    GL2 gl = drawable.getGL().getGL2();
    init(gl, width, height);
    super.init(gl, width, height);
  }

  public void display(GLAutoDrawable drawable) {
    int width = drawable.getWidth();
    int height = drawable.getHeight();
    GL2 gl = drawable.getGL().getGL2();

    renderer2D.clear(gl);
    render(gl, width, height);
    renderer2D.render(gl, subcomponents, width, height);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {}

  
  /* Listeners */
  
  public void addKeyListener(KeyListener listener) {
    window.addKeyListener(listener);
  }


  public void addMouseListener(MouseListener listener) {
    window.addMouseListener(listener);
  }


  public void addWindowListener(WindowListener listener) {
    window.addWindowListener(listener);
  }

}
