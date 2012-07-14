package scene.geometry;

import javax.media.opengl.GL2;

import math.Vector3D;

public class Mesh {

  public final Vector3D[] vertices;
  public final Triangle[] triangles;
  
  public Mesh(Vector3D[] vertices, Triangle[] triangles) {
    this.vertices = vertices;
    this.triangles = triangles;
  }

  public void render(GL2 gl) {
    gl.glColor3d(1, 1, 1);
    gl.glBegin(GL2.GL_TRIANGLES);
    {
      for (Triangle triangle : triangles) {
        for (int i = 0; i < 3; i++) {
          Vector3D uvw = triangle.getTextureUV(i);
          gl.glTexCoord2dv(uvw.toDouble(), 0);
          
          Vector3D vertex = triangle.getVertex(i);
          gl.glVertex3dv(vertex.toDouble(), 0);
          
          Vector3D normal = triangle.getNormal(i);
          gl.glNormal3dv(normal.toDouble(), 0);
        }
      }
    }
    gl.glEnd();
  }
}
