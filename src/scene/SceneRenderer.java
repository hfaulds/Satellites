package scene;


import geometry.Vector2D;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import render.Renderer2D;
import render.Renderer3D;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

public class SceneRenderer extends MouseAdapter {

  private static final int ZOOM_RATE    = 10;
  private static final int ZOOM_DEFAULT = 20;
  
  private static final int PAN_BUTTON   = MouseEvent.BUTTON1;
  
  private final Renderer3D renderer3D = new Renderer3D();
  private final Renderer2D renderer2D = new Renderer2D();

  private final Scene scene;
  public double zoom = ZOOM_DEFAULT;
  
  private Vector2D cameraPos = new Vector2D();
  private Vector2D startMousePos = new Vector2D();
  private Vector2D endMousePos = new Vector2D();
  private boolean bPanning = false;
  
  public SceneRenderer(Scene scene) {
    this.scene = scene;
  }

  public void init(GLAutoDrawable drawable) {
    renderer3D.init(glFromDrawable(drawable), scene);
  }
  
  public void render(GLAutoDrawable drawable) {
    final GL2 gl = drawable.getGL().getGL2();
    
    int width = drawable.getWidth();
    int height = drawable.getHeight();
    
    if(bPanning) {
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      cameraPos._add(direction);
    }
    
    renderer3D.clear(gl);
    
    renderer3D.preRender(gl, cameraPos , (double)width/height, zoom);
    renderer3D.render(gl, scene);
    
    renderer2D.preRender(gl, width, height);
    renderer2D.render(gl, scene.ui, width, height);
  }
  
  public void reshape(GLAutoDrawable drawable, int width, int height) {
    GL2 gl = drawable.getGL().getGL2();
    renderer3D.reshape(gl, width, height);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    bPanning = !(e.getButton() == PAN_BUTTON);
    endMousePos._setFromScreen(e.getX(), e.getY());
  }
  
  @Override
  public void mousePressed(MouseEvent e) {
    bPanning = !(e.getButton() == PAN_BUTTON);
    endMousePos._set(startMousePos._setFromScreen(e.getX(), e.getY()));
  }
  
  @Override
  public void mouseReleased(MouseEvent e) {
    bPanning = (e.getButton() == PAN_BUTTON);
  }
  
  @Override
  public void mouseWheelMoved(MouseEvent e) {
    this.zoom =  Math.max(Math.abs(this.zoom - e.getWheelRotation() * ZOOM_RATE), ZOOM_RATE);
    renderer3D.updateMatrices();
  }

  private GL2 glFromDrawable(GLAutoDrawable drawable) {
    return drawable.getGL().getGL2();
  }
}
