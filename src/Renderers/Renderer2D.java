package Renderers;

import javax.media.opengl.GL2;

import Graphics.UI.UIComponent;

public class Renderer2D {
  public void preRender(GL2 gl, double width, double height) {
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0, width, height, 0, 0, 1);
    gl.glDisable(GL2.GL_DEPTH_TEST);
    gl.glDisable(GL2.GL_CULL_FACE);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public void render(GL2 gl, UIComponent[] components) {
    for(UIComponent component : components)
      component.render(gl);
  }
}
