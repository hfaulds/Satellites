package Scene;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

import Actors.Actor;
import Math.Vector2D;

public class SceneRenderer implements GLEventListener, MouseWheelListener {

  private final Scene scene;
  
  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 10;
  public double zoom = ZOOM_DEFAULT;
  
  private static GLU glu = new GLU();
  
  private boolean matrixChanged         = true;
  private static double[] modelMatrix          = new double[16];
  private static double[] projectionMatrix     = new double[16];
  private static int[] viewportMatrix          = new int[4];
  
  public SceneRenderer(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();
    gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LEQUAL);
    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
  }
  
  public void clear(GLAutoDrawable drawable) {
    final GL2 gl = drawable.getGL().getGL2();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    final GL2 gl = drawable.getGL().getGL2();
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();

    float widthHeightRatio = (float) drawable.getWidth() / (float) drawable.getHeight();
    glu.gluPerspective(45, widthHeightRatio, 1, 1000);
    glu.gluLookAt(0, 0, zoom, 0, 0, 0, 0, 1, 0);

    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    
    if(matrixChanged) {
      gl.glGetIntegerv(GL2.GL_VIEWPORT, viewportMatrix, 0);
      gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelMatrix, 0);
      gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projectionMatrix, 0);
      matrixChanged = false;
    }

    gl.glEnable(GL.GL_MULTISAMPLE);

    for(Actor satellite : scene.actors) {
      satellite.render(gl, glu);
    }
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL2 gl = drawable.getGL().getGL2();

    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    
    float ratio = (float) width / (float) height;
    glu.gluPerspective(50.0f, ratio, 1.0, 1000.0);
    
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  }
  
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    this.zoom += e.getWheelRotation() * ZOOM_RATE;
    matrixChanged = true;
  }
  
  public static Vector2D project(Vector2D position) {
    double[] player = new double[4];
    
    glu.gluProject(position.x, position.y, Vector2D.Z, 
        modelMatrix, 0,
        projectionMatrix, 0, 
        viewportMatrix, 0,
        player, 0);
    
    return new Vector2D(player[0], player[1]);
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {
  }
}
