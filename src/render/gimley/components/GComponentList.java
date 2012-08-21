package render.gimley.components;

import java.util.ArrayList;
import java.util.Arrays;

import javax.media.opengl.GL2;

@SuppressWarnings("serial")
public class GComponentList extends ArrayList<GComponent>  {
  
  private GComponent focus;
  
  public GComponentList(GComponent focus, GComponent ... components) {
    addAll(Arrays.asList(components));
    this.focus = focus;
  }
  
  public GComponent getFocus() {
    return focus;
  }
  
  public void setFocus(GComponent focus) {
    if(contains(focus)) {
      remove(focus);
      add(0, focus);
    }
    this.focus = focus;
  }

  public void render(GL2 gl, int width, int height) {
    for(GComponent component : this) {
      component.render(gl, width, height);
    }
  }
  
}
