package Scene;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import Math.Vector2D;
import Renderers.Renderer2D;
import Renderers.Renderer3D;

public class Camera extends MouseAdapter {

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
  
  public Camera(Scene scene) {
    this.scene = scene;
  }

  public void init(GLAutoDrawable drawable) {
    renderer3D.init(glFromDrawable(drawable));
  }
  
  public void render(GLAutoDrawable drawable) {
    final GL2 gl = drawable.getGL().getGL2();
    
    double width = drawable.getWidth();
    double height = drawable.getHeight();
    
    if(bPanning) {
      Vector2D direction = endMousePos.sub(startMousePos).divide(1000);
      cameraPos._add(direction);
    }
    
    renderer3D.clear(gl);
    renderer3D.preRender(gl, cameraPos , width/height, zoom);
    renderer3D.render(gl, scene.actors);
    renderer2D.preRender(gl, width, height);
    renderer2D.render(gl, scene.ui);
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    renderer3D.reshape(glFromDrawable(drawable), x, y, width, height);
  }
  
  @Override
  public void mouseDragged(MouseEvent e) {
    endMousePos._setFromScreen(e.getPoint());
  }
  
  @Override
  public void mousePressed(MouseEvent e) {
   if(e.getButton() == PAN_BUTTON) {
     bPanning = true;
     endMousePos._set(startMousePos._setFromScreen(e.getPoint()));
   }
  }
  
  @Override
  public void mouseReleased(MouseEvent e) {
    bPanning = !(e.getButton() == PAN_BUTTON);
  }
  
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    this.zoom =  Math.abs(this.zoom + e.getWheelRotation() * ZOOM_RATE);
    renderer3D.updateMatrices();
  }

  private GL2 glFromDrawable(GLAutoDrawable drawable) {
    return drawable.getGL().getGL2();
  }
}
