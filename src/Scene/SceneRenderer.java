package Scene;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

import Actors.Actor;

public class SceneRenderer implements GLEventListener {

  private static GLU glu = new GLU();
  private final Scene scene;
  
  public double zoom = 20;
  
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

    gl.glEnable(GL.GL_MULTISAMPLE);

    for(Actor satellite : scene.actors) {
      satellite.render(gl, glu);
    }
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL2 gl = drawable.getGL().getGL2();
    if (height <= 0) {
        height = 1;
    }
    float h = (float) width / (float) height;
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluPerspective(50.0f, h, 1.0, 1000.0);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {
  }
}
