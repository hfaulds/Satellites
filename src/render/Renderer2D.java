package render;

import javax.media.opengl.GL2;

import render.gimley.components.GComponent;

public class Renderer2D {
  public void preRender(GL2 gl, double width, double height) {
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0, width, height, 0, 0, 1);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glDisable(GL2.GL_LIGHTING);
    gl.glScalef(1, -1, 1);
    gl.glTranslated(0, -height, 0);
  }

  public void render(GL2 gl, GComponent[] components, int width, int height) {
    for(GComponent component : components)
      component.render(gl, width, height);
    gl.glEnable(GL2.GL_LIGHTING);
  }
  

  public static void drawFillRect(GL2 gl, double x, double y, double width, double height) {
    gl.glBegin(GL2.GL_QUADS);
    drawRect(gl, x, y, width, height);
    gl.glEnd();
  }
  
  public static void drawLineRect(GL2 gl, double x, double y, double width, double height) {
    gl.glBegin(GL2.GL_LINE_LOOP);
    drawRect(gl, x, y, width, height);
    gl.glEnd();
  }

  private static void drawRect(GL2 gl, double x, double y, double width,
      double height) {
    gl.glVertex2d(x        , y + height );
    gl.glVertex2d(x + width, y + height );
    gl.glVertex2d(x + width, y          );
    gl.glVertex2d(x        , y          );
  }
}
