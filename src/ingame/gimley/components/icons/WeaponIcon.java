package ingame.gimley.components.icons;

import ingame.actors.weapons.Weapon;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import core.geometry.Vector2D;
import core.gimley.components.GComponent;
import core.render.Renderer2D;
import core.render.material.Colour;

public class WeaponIcon extends GComponent {

  public static final int SIZE = 40;
  
  private final Weapon weapon;
  
  private final Colour fillColour = new Colour(.5,.5,.5,1);
  private final Colour outlineColour = new Colour(1,1,1,1);
  private final Colour textColour = new Colour(1,1,1,1);

  public WeaponIcon(GComponent parent, Weapon weapon) {
    super(parent, new Vector2D(), SIZE, SIZE);
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
  }

}
