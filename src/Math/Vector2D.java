package Math;


public class Vector2D {
  
  public double x;
  public double y;
  
  
  /* Constructors */
  public Vector2D() {
    this(0,0);
  }
  
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  
  /* Mutable */
  public Vector2D _add(Vector2D other) {
    this.x += other.x;
    this.y += other.y;
    return this;
  }

  public Vector2D _sub(Vector2D other) {
    this.x -= other.x;
    this.y -= other.y;
    return this;
  }
  
  public Vector2D _mult(double n) {
    this.x *= n;
    this.y *= n;
    return this;
  }
  
  public Vector2D _divide(double n) {
    this.x /= n;
    this.y /= n;
    return this;
  }

  public Vector2D _normalize() {
    double mag = this.magnitude();
    this.x /= mag;
    this.y /= mag;
    return this;
  }
  
  public Vector2D _rotate(double angle) {
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);
    double x = this.x*cos - this.y*sin;
    double y = this.x*sin + this.y*cos;
    this.x = x;
    this.y = y;
    return this;
  }
  
  
  /* Non-Mutable */
  public Vector2D add(Vector2D other) {
    return new Vector2D(x + other.x, y + other.y);
  }
  
  public Vector2D sub(Vector2D other) {
    return new Vector2D(x - other.x, y - other.y);
  }

  public Vector2D mult(double n) {
    return new Vector2D(x*n, y*n);
  }

  public Vector2D divide(double n) {
    return new Vector2D(x/n, y/n);
  }

  public Vector2D normalized() {
    double mag = this.magnitude();
    return new Vector2D(x / mag, y / mag);
  }
  
  /* Other */
  public double magnitude() {
    return Math.sqrt(x*x + y*y);
  }
  
  public double distanceTo(Vector2D other) {
    return other.sub(this).magnitude();
  }

  public static double dot(Vector2D a, Vector2D b) {
    return (a.x * b.x) + (a.y * b.y);
  }
}
