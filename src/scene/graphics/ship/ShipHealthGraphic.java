package scene.graphics.ship;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import math.Rotation;
import math.Vector2D;
import scene.actors.ShipActor;
import scene.graphics.Graphic;
import scene.material.Colour;

public class ShipHealthGraphic implements Graphic {

  private static final double HEALTH_RADIUS     = ShipControlGraphic.CONTROL_RADIUS + .2;
  private static final int HEALTH_RATIO         = 10;
  private static final double HEALTH_WIDTH      = 0.15;
  private static final Colour HEALTH_COLOUR     = new Colour(1,0,0);
  
  private static final int SEGMENTS             = ShipActor.MAX_HEALTH * 4 / HEALTH_RATIO;
  private static final double SEGMENT_INCREMENT = 2 * Math.PI / SEGMENTS;

  private final int start = SEGMENTS * 7 / 8;
  
  private final ShipActor actor;

  public ShipHealthGraphic(ShipActor actor) {
    this.actor = actor;
  }
  
  @Override
  public void init(GL2 gl, GLU glu) {
  }

  @Override
  public void render(GL2 gl, GLU glu, Vector2D pos, Rotation rot) {
    gl.glDisable(GL2.GL_LIGHTING);

    gl.glTranslated(pos.x, pos.y, Vector2D.Z);
    
    {
      gl.glColor3fv(Colour.WHITE.toFloat(), 0);
      this.drawSegment(gl, start - 1, start + SEGMENTS/4+1 , HEALTH_WIDTH * 2);
    }
 
    {
      gl.glColor3fv(Colour.BLACK.toFloat(), 0);
      int start = this.start + (actor.health / HEALTH_RATIO);
      int end = this.start + (ShipActor.MAX_HEALTH / HEALTH_RATIO);
      this.drawSegment(gl, start, end, HEALTH_WIDTH);
    }
    
    {
      gl.glColor3fv(HEALTH_COLOUR.toFloat(), 0);
      int end = start + (actor.health / HEALTH_RATIO);
      this.drawSegment(gl, start, end, HEALTH_WIDTH);
    }
   
    
    gl.glEnable(GL2.GL_LIGHTING);
  }

  private void drawSegment(GL2 gl, int start, int end, double thickness) {
    thickness /= 2;

    for(int i=start; i < end ; i++){
      gl.glBegin(GL2.GL_POLYGON);
      pointOnCircle(gl, thickness, i);
      pointOnCircle(gl, -thickness, i);
      pointOnCircle(gl, thickness, i+1);
      gl.glEnd();

      gl.glBegin(GL2.GL_POLYGON);
      pointOnCircle(gl, thickness, i+1);
      pointOnCircle(gl, -thickness, i+1);
      pointOnCircle(gl, -thickness, i);
      gl.glEnd();
    }
    gl.glEnd();
  }

  private void pointOnCircle(GL2 gl, double thickness, int i) {
    gl.glVertex3d(
        Math.cos(i * SEGMENT_INCREMENT) * (HEALTH_RADIUS + thickness), 
        Math.sin(i * SEGMENT_INCREMENT) * (HEALTH_RADIUS + thickness),
        Vector2D.Z
      );
  }

}
