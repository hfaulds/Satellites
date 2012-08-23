package gimley.components;


import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.render.Renderer2D;


public class TopBar extends GComponent {

  private Vector2D mouseDragOffset;
  private final String title;

  public TopBar(GComponent parent, String title) {
    super(parent, new Vector2D(0, parent.height));
    this.width = parent.width;
    this.height = 15;
    this.title = title;
  }
  
  @Override
  public void mousePressed(Vector2D click, MouseEvent e) {
    mouseDragOffset = click.sub(parent.position);
  }
  
  @Override
  public void mouseDragged(Vector2D click, MouseEvent e) {
    if(mouseDragOffset != null) {
      this.parent.position._set(click.sub(mouseDragOffset));
    }
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(1.0, 1.0, 1.0, 0.8);
    Renderer2D.drawFillRect(gl, 
        parent.position.x + position.x, 
        parent.position.y + position.y, 
        parent.width, 
        this.height);
    
    gl.glColor4d(1.0, 1.0, 1.0, 1);
    Renderer2D.drawLineRect(gl, 
        parent.position.x + position.x, 
        parent.position.y + position.y, 
        parent.width, 
        this.height, 
        0.9f);
    
    gl.glColor4d(0.4, 0.4, 0.4, 1.0);
    Renderer2D.drawText(gl, 
        parent.position.x + position.x + 5, 
        parent.position.y + position.y + 3, 
        Renderer2D.fitString(gl, title, parent.width - 10));
  }

}
