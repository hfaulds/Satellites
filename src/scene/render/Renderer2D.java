package scene.render;


import javax.media.opengl.GL2;

import scene.ui.Sprite;


public class Renderer2D {
  public void preRender(GL2 gl, double width, double height) {
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0, width, height, 0, 0, 1);
    gl.glDisable(GL2.GL_DEPTH_TEST);
    gl.glDisable(GL2.GL_CULL_FACE);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glDisable(GL2.GL_LIGHTING);
    gl.glScalef(1, -1, 1);
    gl.glTranslated(0, -height, 0);
  }

  public void render(GL2 gl, Sprite[] components) {
    for(Sprite component : components)
      component.render(gl);
    gl.glEnable(GL2.GL_LIGHTING);
  }
}