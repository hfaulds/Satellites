package geometry;

import math.Vector3D;

public class Box {

  public final Vector3D max, min;
  
  public Box(Vector3D max, Vector3D min) {
    this.max = max;
    this.min = min;
  }
  
  public static Box createBoundingBox(Mesh mesh) {
    Vector3D max = new Vector3D(mesh.vertices[0]);
    Vector3D min = new Vector3D(mesh.vertices[0]);
    
    for(Vector3D Vector3D : mesh.vertices)
    {
      max.x = Math.max(Vector3D.x, max.x);
      max.y = Math.max(Vector3D.y, max.y);
      max.z = Math.max(Vector3D.z, max.z);
      
      min.x = Math.min(Vector3D.x, min.x);
      min.y = Math.min(Vector3D.y, min.y);
      min.z = Math.min(Vector3D.z, min.z);
    }

    return new Box(max, min);
  }

  public boolean contains(Vector3D point) {
    return (point.x <= max.x) && (point.x >= min.x) 
        && (point.y <= max.y) && (point.y >= min.y) 
        && (point.z <= max.z) && (point.z >= min.z);
  }
}
