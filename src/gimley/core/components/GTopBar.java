package gimley.core.components;


import gimley.core.ActionListener;
import gimley.core.components.buttons.GButton;

import javax.media.opengl.GL2;

import com.jogamp.newt.event.MouseEvent;

import core.geometry.Vector2D;
import core.render.Renderer2D;


public class GTopBar extends GComponent {

  private Vector2D mouseDragOffset;
  private final String title;
  
  private final GButton minimise = new GButton(parent, new Vector2D(parent.width - 28, parent.height + 2), 11, 11, "_");
  private final GButton close = new GButton(parent, new Vector2D(parent.width - 13, parent.height + 2), 11, 11, "X");
  
  public GTopBar(GComponent parent, String title) {
    this(parent, title, true, false);
  }

  public GTopBar(final GComponent parent, String title, boolean bMinimise, boolean bClose) {
    super(parent, new Vector2D(0, parent.height));
    this.width = parent.width;
    this.height = 15;
    this.title = title;
    
    if(bMinimise) {
      add(minimise);
      if(!bClose) {
        minimise.position.x += 11;
      }
    }
    
    if(bClose) {
      add(close);
    }
    
    minimise.addActionListener(new ActionListener() {
      @Override
      public void action() {
        
      }
    });

    close.addActionListener(new ActionListener() {
      @Override
      public void action() {
        parent.setVisible(false);
      }
    });
    
  }
  
  
  /* Mouse Controls */
  
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
  
  
  /* Rendering */
  
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
    
    super.render(gl, width, height);
  }

}