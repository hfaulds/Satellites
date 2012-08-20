package render.gimley.components;

import geometry.Vector2D;
import geometry.Vector3D;

import javax.media.opengl.GL2;

import render.Renderer2D;

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
  
  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(1.0, 1.0, 1.0, 0.8);
    Renderer2D.drawFillRect(gl, position.x, position.y, parent.width, this.height);
    gl.glColor4d(1.0, 1.0, 1.0, 1);
    Renderer2D.drawLineRect(gl, position.x, position.y, parent.width, this.height, 0.9f);
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
