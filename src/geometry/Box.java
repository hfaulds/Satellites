package geometry;


public class Box {

  private final Vector3D max, min;
  private final Vector2D position;
  
  public Box(Vector3D max, Vector3D min, Vector2D position) {
    this.max = max;
    this.min = min;
    this.position = position;
  }
  
  public static Box createBoundingBox(Mesh mesh, Vector2D position) {
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

    return new Box(max, min, position);
  }

  public double minX() {
    return min.x + position.x;
  }
  
  public double maxX() {
    return max.x + position.x;
  }
  
  public double minY() {
    return min.y + position.y;
  }
  
  public double maxY() {
    return max.y + position.y;
  }
  
  public static boolean boxesIntersect(Box a, Box b) {
    return  a.minX() < b.maxX() && 
            a.maxX() > b.minX() &&
            a.minY() < b.maxY() && 
            a.maxY() > b.minY();
  }
}
