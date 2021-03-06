package ingame.gimley.icons;

import ingame.actors.weapons.WeaponActor;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;
import core.render.material.Colour;

public class WeaponIcon extends GComponent {

  public static final int SIZE = 40;
  
  private final WeaponActor weapon;
  
  private final Colour fillColour = new Colour(0.7,.2,.2,1);
  private final Colour outlineColour = new Colour(1,1,1,1);
  private final Colour textColour = new Colour(1,1,1,1);

  public WeaponIcon(GComponent parent, Vector2D position, WeaponActor weapon) {
    super(parent, position, SIZE, SIZE);
    this.weapon = weapon;
  }
  
  public void render(GL2 gl, int width, int height) {
    Vector2D screenPosition = this.getScreenPosition();

    gl.glColor4fv(fillColour.toFloat(), 0);
    Renderer2D.drawFillRect(gl, 
        screenPosition.x, 
        screenPosition.y, 
        this.width, 
        this.height, 
        5);

    gl.glColor4fv(outlineColour.toFloat(), 0);
    Renderer2D.drawLineRect(gl, 
        screenPosition.x,
        screenPosition.y,
        this.width,
        this.height,
        1, 
        5);

    gl.glColor4fv(textColour.toFloat(), 0);
    double nameHeight = Renderer2D.getTextSize(gl, weapon.getName()).y;
    Renderer2D.drawText(gl,
        screenPosition.x + 5,
        screenPosition.y + this.height - nameHeight - 5,
        GLUT.BITMAP_HELVETICA_10,
        weapon.getName());
    

    String ammoQuantity = weapon.getAmmo().getQuantityString();
    double quantityWidth = Renderer2D.getTextSize(gl, ammoQuantity).x;
    
    Renderer2D.drawText(gl, 
        screenPosition.x + this.width - quantityWidth - 5,
        screenPosition.y + 5,
        ammoQuantity);
  }

}
