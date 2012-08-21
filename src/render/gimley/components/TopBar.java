package render.gimley.components;

import geometry.Vector2D;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import render.Renderer2D;

public class TopBar extends GComponent {

  private Vector2D mouseDragOffset;

  public TopBar(GComponent parent) {
    super(parent, new Vector2D(parent.position).add(new Vector2D(0, parent.height)));
    this.width = parent.width;
    this.height = 15;
  }
  
  @Override
  public void mousePressed(Vector2D click, MouseEvent e) {
    mouseDragOffset = click.sub(new Vector2D(position));
  }
  
  @Override
  public void mouseDragged(Vector2D click, MouseEvent e) {
    if(mouseDragOffset != null) {
      this.position._set(click.sub(mouseDragOffset));
      this.parent.position._set(position.sub(new Vector2D(0, parent.height)));
    }
  }
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(1.0, 1.0, 1.0, 0.8);
    Renderer2D.drawFillRect(gl, position.x, position.y, parent.width, this.height);
    gl.glColor4d(1.0, 1.0, 1.0, 1);
    Renderer2D.drawLineRect(gl, position.x, position.y, parent.width, this.height, 0.9f);
  }

}
