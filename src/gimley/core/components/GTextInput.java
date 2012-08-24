package gimley.core.components;

import gimley.core.components.button.ActionListener;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.KeyEvent;

import core.geometry.Vector2D;
import core.render.Renderer2D;

public class GTextInput extends GComponent {

  private static final int MAX_INPUT = 40;
  
  public String input = "";

  public GTextInput(GComponent parent, Vector2D position, int width, int height) {
    super(parent, position);
    this.width = width;
    this.height = height;
  }

  @Override
  public void render(GL2 gl, int width, int height) {
    gl.glColor4d(0.6, 0.6, 0.6, 1.0);
    Renderer2D.drawFillRect(gl, 
        parent.position.x + position.x , 
        parent.position.y + position.y, 
        this.width, this.height
        );
    
    gl.glColor4d(1.0, 1.0, 1.0, 1.0);
    Renderer2D.drawLineRect(gl, 
        parent.position.x + position.x ,
        parent.position.y + position.y, 
        this.width, this.height, 0.9f
        );
    
    Renderer2D.drawText(gl, 
        parent.position.x + position.x + 2 , 
        parent.position.y + position.y + 5, 
        Renderer2D.fitString(gl, input, this.width)
        );
  }
  
  public String getInput() {
    return input;
  }

  public void addChar(char c) {
    if(input.length() <= MAX_INPUT)
      input = input + c;
  }

  private void backSpace() {
    int length = input.length();
    if(length > 0)
      input = input.substring(0, length - 1);
  }

  public void clearInput() {
    input = "";
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    switch(keyCode) {
      case 10: // enter
        if(getInput().length() > 0) {
          for(ActionListener listener : listeners)
            listener.action();
        }
        break;
      case 8: // backspace
        backSpace();
        break;
      default:
        char character = (char)keyCode;
        if(!e.isShiftDown())
          character = Character.toLowerCase(character);
        addChar(character);
    }
  }

}
