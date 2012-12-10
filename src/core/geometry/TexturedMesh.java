package core.geometry;

import javax.media.opengl.GL2;

import core.render.material.Material;


public class TexturedMesh extends Mesh {

  private final Material material;

  public TexturedMesh(Vector3D[] vertices, Triangle[] triangles, String name, Material material) {
    super(vertices, triangles, name);
    this.material = material;
  }
  
  public void render(GL2 gl) {
    material.startRender(gl);
    super.render(gl);
    material.stopRender(gl);
  }
}
