package geometry;


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
  
  public boolean collides(Box other) {
    if (this.max.x < other.min.x || 
        this.max.y < other.min.y || 
        this.min.x > other.max.x || 
        this.min.y > other.max.y) 
    {
        return false;
    }
    return true;
  }
}
