package core.render;

import gimley.core.GComponentList;

import java.awt.Font;
import java.awt.geom.Rectangle2D;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import core.geometry.Vector2D;

public class Renderer2D {

  private static final Font font = new Font("Helvetica", Font.PLAIN, 12);
  private static final TextRenderer textRenderer = new TextRenderer(font);
  
  public void render(GL2 gl, GComponentList components, int width, int height) {
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0, width, height, 0, 0, 1);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glScalef(1, -1, 1);
    gl.glTranslated(0, -height, 0);
    
    gl.glDepthMask(false);

    gl.glDisable(GL2.GL_LIGHTING);
    gl.glDisable(GL2.GL_CULL_FACE);
    
    components.render(gl, width, height);
    
    gl.glEnable(GL2.GL_CULL_FACE);
    gl.glEnable(GL2.GL_LIGHTING);
    
    gl.glDepthMask(true);
  }
  
  public static Vector2D getTextSize(GL2 gl, String text) {
    Rectangle2D bounds = textRenderer.getBounds(text);
    return new Vector2D(bounds.getWidth(), bounds.getHeight());
  }

  public static void drawFillRect(GL2 gl, double x, double y, double width, double height) {
    gl.glBegin(GL2.GL_QUADS);
    drawRect(gl, x, y, width, height);
    gl.glEnd();
  }
  
  public static void drawLineRect(GL2 gl, double x, double y, double width, double height, float thickness) {
    gl.glLineWidth(thickness);
    gl.glBegin(GL2.GL_LINE_LOOP);
    drawRect(gl, x - thickness, y - thickness, width + 2*thickness, height + 2*thickness);
    gl.glEnd();
  }

  private static void drawRect(GL2 gl, double x, double y, double width,
      double height) {
    gl.glVertex2d(x        , y + height);
    gl.glVertex2d(x + width, y + height);
    gl.glVertex2d(x + width, y         );
    gl.glVertex2d(x        , y         );
  }

  public static void drawText(GL2 gl, double x, double y, String text) {
    drawText(gl, x, y, GLUT.BITMAP_HELVETICA_12, text);
  }
  

  public static void drawText(GL2 gl, double x, double y, int font, String text) {
    GLUT glut = new GLUT();
    gl.glWindowPos2d(x, y);
    glut.glutBitmapString(font, text);
  }

  public static String fitString(GL2 gl, String message, int width) {
    if(Renderer2D.getTextSize(gl, message).y > width) {
      return fitString(gl, message.substring(1), width);
    } else {
      return message;
    }
  
  }

  public void clear(GL2 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
  }
  
}
