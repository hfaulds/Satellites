package Math;


public class Vector2D {
  
  public double x;
  public double y;
  
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2D() {
    this(0,0);
  }

  public void _add(Vector2D other) {
    this.x += other.x;
    this.y += other.y;
  }

  public Vector2D sub(Vector2D other) {
    return new Vector2D(x-other.x, y-other.y);
  }
  
  public void _multiply(double n) {
    this.x *= n;
    this.y *= n;
  }

  public Vector2D multiply(double n) {
    return new Vector2D(x*n, y*n);
  }

  public Vector2D divide(double n) {
    return new Vector2D(x/n, y/n);
  }

  public double magnitude() {
    return Math.sqrt(x*x + y*y);
  }
  
  public double distanceTo(Vector2D other) {
    return other.sub(this).magnitude();
  }

  public Vector2D _normalize() {
    double mag = this.magnitude();
    this.x /= mag;
    this.y /= mag;
    return this;
  }


}
