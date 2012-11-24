package core.geometry;

import javax.media.opengl.GL2;

import core.render.material.Material;

public class Mesh {

  public final Vector3D[] vertices;
  public final Triangle[] triangles;
  
  public Material material = new Material();
  
  public Mesh() {
    this(new Vector3D[]{new Vector3D()}, new Triangle[0]);
  }
  
  public Mesh(Vector3D[] vertices, Triangle[] triangles) {
    this.vertices = vertices;
    this.triangles = triangles;
  }

  public void render(GL2 gl) {
    for (Triangle triangle : triangles) {
      gl.glBegin(GL2.GL_TRIANGLES);
      for (int i = 0; i < 3; i++) {
        Vector3D uvw = triangle.getTextureUV(i);
        Vector3D vertex = triangle.getVertex(i);
        Vector3D normal = triangle.getNormal(i);

        gl.glNormal3dv(normal.toDouble(), 0);
        gl.glTexCoord3dv(uvw.toDouble(), 0);
        gl.glVertex3dv(vertex.toDouble(), 0);
      }
      gl.glEnd();
    }
  }

  public Mesh setMaterial(Material material) {
    this.material = material;
    return this;
  }
}
