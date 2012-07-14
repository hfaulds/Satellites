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
          gl.glNormal3dv(vertex.normal.toDouble(), 0);
        }
      }
    }
    gl.glEnd();
  }
  
  public Mesh _smoothNormals() {
    for(Vector3D vert : this.vertices)
      vert.normal = new Vector3D();
    
    for (Triangle triangle : triangles) {
      Vector3D[] verts = triangle.vertices;
      
      Vector3D avec = verts[0].subtract(verts[1]);
      Vector3D bvec = verts[0].subtract(verts[2]);
      
      Vector3D triangleNormal = avec.crossProduct(bvec);
      

      for(Vector3D vert : verts)
      {
        vert.normal = vert.normal.add(triangleNormal);
      }
    }
    
    for(Vector3D vert : this.vertices)
      vert.normal._normalize();
    
    return this;
  }
}
