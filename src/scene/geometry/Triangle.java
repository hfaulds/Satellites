package scene.geometry;

import math.Vector3D;

public class Triangle {
  
  public final Vector3D[] vertices;
  public final Vector3D[] uvws;
  
  public Triangle(Vector3D[] vertices, Vector3D[] uvws) {
    this.vertices = vertices;
    this.uvws = uvws;
  }

  public Vector3D getTextureUV(int i) {
    return uvws[i];
  }

  public Vector3D getVertex(int i) {
    return vertices[i];
  }

}
