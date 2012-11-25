package ingame.actors;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.geometry.Rotation;
import core.geometry.Vector2D;
import core.render.material.Colour;
import core.render.material.Material;

public class Planet001Actor extends Actor {

  private static final Colour   BLUE      = new Colour(0, 0.1, 0.3, 1);
  private static final Material MATERIAL  = new Material(BLUE, BLUE, BLUE, BLUE, 127);
  private static final Mesh     MESH      = MeshLoader.loadMesh("Planet 1000.obj").setMaterial(MATERIAL);
  
  public static final int       MASS      = 10000; 

  public Planet001Actor(Vector2D position, Rotation rotation, double mass, int id) {
    super(position, rotation, mass, MESH, id);
  }
  
  public Planet001Actor(double x, double y) {
    super(new Vector2D(x,y), new Rotation(), MASS, MESH);
  }

}
