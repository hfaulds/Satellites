package geometry;

import math.Vector3D;

public class Triangle {
  
  public final Vector3D[] vertices;
  public final Vector3D[] normals;
  public final Vector3D[] uvws;
  
  public Triangle(Vector3D[] vertices, Vector3D[] normals, Vector3D[] uvws) {
    this.vertices = vertices;
    this.normals = normals;
    this.uvws = uvws;
  }

  public Triangle(Vector3D[] vertices) {
    this.vertices = vertices;
    this.normals = new Vector3D[0];
    this.uvws = new Vector3D[0];
  }

  public Vector3D getTextureUV(int i) {
    return uvws[i];
  }

  public Vector3D getVertex(int i) {
    return vertices[i];
  }

  public Vector3D getNormal(int i) {
    return normals[i];
  }

}
