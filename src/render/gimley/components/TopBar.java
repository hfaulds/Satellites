package render.gimley.components;

import geometry.Vector2D;
import geometry.Vector3D;

import javax.media.opengl.GL2;

import render.Renderer2D;

import com.jogamp.opengl.util.gl2.GLUT;

public class TopBar extends GComponent {

  private Vector2D mouseDragOffset;

  public TopBar(GComponent parent) {
    super(parent, new Vector2D(parent.position).add(new Vector2D(0, parent.height)));
    this.width = parent.width;
    this.height = 15;
  }
  
  @Override
  public void mousePressed(Vector2D click, int button) {
    mouseDragOffset = click.sub(new Vector2D(position));
  }
  
  @Override
  public void mouseDragged(Vector2D click, int button) {
    if(mouseDragOffset == null)
      mouseDragOffset = click.sub(new Vector2D(position));
    else {
      this.position._set(new Vector3D(click.sub(mouseDragOffset)));
      this.parent.position._set(position.subtract(new Vector3D(0, parent.height, 0)));
    }
  }

  private final GLUT glut = new GLUT();
  
  @Override
  public void render(GL2 gl, int width, int height) {
    for(int i=150; i < 750; i+=20){
      gl.glWindowPos2d(5,i);
      glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, String.valueOf(i));
      
      gl.glBegin(GL2.GL_LINES);
      gl.glVertex2d(10, i);
      gl.glVertex2d(100, i);
      gl.glEnd();
    }
    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawFillRect(gl, position.x, position.y, parent.width, this.height);
  }

  @Override
  public boolean testClick(Vector2D click) {
    boolean a = click.x >= position.x;
    boolean b = click.y >= position.y;
    boolean c = click.x < position.x + width;
    boolean d = click.y < position.y + height;
    return a && b && c && d;
  }

}
