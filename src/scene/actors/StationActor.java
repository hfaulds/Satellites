package scene.actors;


import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import scene.Actor;

import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.render.material.Colour;
import core.render.material.Material;

public class StationActor extends Actor {
  
  private static final Material shieldMaterial = new Material(new Colour(), new Colour(1, 1, 1, 0.1), new Colour(), new Colour(), 127);
  
  private static final double MASS = 0.1;
  public static final Mesh MESH    = MeshLoader.loadMesh("Station-Mk2.obj");
  
  final double shieldRadius = new Vector2D(boundingbox.maxX(), boundingbox.maxY()).distanceTo(new Vector2D(boundingbox.minX(), boundingbox.minY()));
  final int shieldSegments = 25;

  public StationActor(Vector2D position, Rotation rotation, double mass, int id, int owner) {
    super(position, rotation, mass, MESH, id, owner);
  }

  public StationActor(double x, double y) {
    super(new Vector2D(x,y), new Rotation(), MASS, MESH);
  }
  
  @Override
  public void render(GL2 gl, GLU glu) {
    super.render(gl, glu);

    GLUquadric shield = glu.gluNewQuadric();
    shieldMaterial.startRender(gl);
    glu.gluSphere(shield, shieldRadius, shieldSegments, shieldSegments);
    glu.gluDeleteQuadric(shield);
  }

}
