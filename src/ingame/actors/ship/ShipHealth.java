package ingame.actors.ship;


import ingame.actors.ShipActor;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.render.material.Colour;

public class ShipHealth extends Actor {

  private static final double HEALTH_RADIUS     = ShipControl.CONTROL_RADIUS + .2;
  private static final int SEGMENT_RATIO        = 10;
  private static final double HEALTH_WIDTH      = 0.2;
  private static final Colour HEALTH_COLOUR     = new Colour(1,0,0);
  private static final Colour SHIELD_COLOUR     = new Colour(0,0,1);
  
  private static final int SEGMENTS             = ShipActor.MAX_HEALTH * 4 / SEGMENT_RATIO;
  private static final double SEGMENT_INCREMENT = 2 * Math.PI / SEGMENTS;

  private final int startHealth = SEGMENTS * 7 / 8;
  private final int startShield = SEGMENTS * 3 / 8;

  public ShipHealth(ShipActor parent) {
    super(parent, new Vector2D(), new Rotation(), 0, new Mesh());
  }
  
  @Override
  public void init(GL2 gl, GLU glu) {}

  @Override
  public void render(GL2 gl, GLU glu) {
    gl.glDisable(GL2.GL_LIGHTING);

    ShipActor parent = (ShipActor)super.parent;
    
    gl.glTranslated(parent.position.x, parent.position.y, Vector2D.Z);
    int health = startHealth + parent.health / SEGMENT_RATIO;

    
    gl.glDisable(GL2.GL_DEPTH_TEST);
    gl.glDisable(GL2.GL_CULL_FACE);

    drawBackground(gl, startHealth);
    drawBarDepleted(
        gl, 
        health, 
        startHealth + (ShipActor.MAX_HEALTH / SEGMENT_RATIO)
        );
    drawBarLeft(
        gl, 
        HEALTH_COLOUR,
        startHealth,
        health
        );

    int shield = parent.shield / SEGMENT_RATIO;
    int sheildFinish = startShield + (ShipActor.MAX_SHIELD / SEGMENT_RATIO);
    
    drawBackground(gl, startShield);
    drawBarDepleted(
        gl, 
        startShield, 
        sheildFinish - shield
        );
    drawBarLeft(
        gl, 
        SHIELD_COLOUR, 
        sheildFinish - shield, 
        sheildFinish
        );
    
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_CULL_FACE);

    gl.glEnable(GL2.GL_LIGHTING);
  }

  private void drawBarLeft(GL2 gl, Colour colour, int start, int end) {
    gl.glColor3fv(colour.toFloat(), 0);
    this.drawSegment(gl, start, end, HEALTH_WIDTH);
  }

  private void drawBarDepleted(GL2 gl, int start, int end) {
    gl.glColor3fv(Colour.BLACK.toFloat(), 0);
    this.drawSegment(gl, start, end, HEALTH_WIDTH);
  }

  private void drawBackground(GL2 gl, int start) {
    gl.glColor3fv(Colour.WHITE.toFloat(), 0);
    this.drawSegment(gl, start - 1, start + SEGMENTS/4+1 , HEALTH_WIDTH * 2.2);
  }

  private void drawSegment(GL2 gl, int start, int end, double thickness) {
    thickness /= 2;

    for(int i=start; i < end ; i++){
      gl.glBegin(GL2.GL_QUADS);
      pointOnCircle(gl, thickness, i);
      pointOnCircle(gl, -thickness, i);
      pointOnCircle(gl, -thickness, i+1);
      pointOnCircle(gl, thickness, i+1);
      gl.glEnd();
    }
  }

  private void pointOnCircle(GL2 gl, double thickness, int i) {
    gl.glVertex3d(
        Math.cos(i * SEGMENT_INCREMENT) * (HEALTH_RADIUS + thickness), 
        Math.sin(i * SEGMENT_INCREMENT) * (HEALTH_RADIUS + thickness),
        Vector2D.Z
      );
  }

}
