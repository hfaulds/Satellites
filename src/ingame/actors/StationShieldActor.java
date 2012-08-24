package ingame.actors;

import core.Actor;
import core.geometry.Mesh;
import core.geometry.MeshLoader;
import core.render.material.Colour;
import core.render.material.Material;

public class StationShieldActor extends Actor {

  private static final Material MATERIAL = new Material(new Colour(), new Colour(1, 1, 1, 0.1), new Colour(), new Colour(), 127);
  private static final double MASS = 0;
  private static final Mesh MESH   = MeshLoader.loadMesh("Station-Mk2-Shield.obj");
  
  protected StationShieldActor(Actor parent) {
    super(parent, parent.position, parent.rotation, MASS, MESH, MATERIAL);
  }
  
}