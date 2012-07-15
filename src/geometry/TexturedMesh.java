package geometry;

import javax.media.opengl.GL2;

import scene.material.Material;

public class TexturedMesh extends Mesh {

  private final Material material;

  public TexturedMesh(Vector3D[] vertices, Triangle[] triangles, Material material) {
    super(vertices, triangles);
    this.material = material;
  }
  
  public void render(GL2 gl) {
    material.startRender(gl);
    super.render(gl);
    material.stopRender(gl);
  }
}
