package core.geometry;

public class Rotation {

  public double x;
  public double y;
  public double z;
  
  public double mag;
  
  public Rotation() {
    this(0,0,1,0);
  }
  
  public Rotation(Rotation other) {
    this(other.x, other.y, other.z, other.mag);
  }

  private Rotation(double x, double y, double z, double mag) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.mag = mag;
  }

  public void _add(double n) {
    this.mag += n;
  }

  public double toDegrees() {
    return this.mag * 180/Math.PI;
  }

  public void _add(Rotation other) {
    /* Normalize if needed*/
    this.x      += other.x;
    this.y      += other.y;
    this.z      += other.z;
    this.mag    += other.mag;
  }

  public void _set(Rotation other) {
    this.x = other.x;
    this.y = other.x;
    this.z = other.z;
    this.mag = other.mag;
  }

  public Rotation mult(double dt) {
    return new Rotation(x, y, z, mag*dt);
  }

  public static Rotation XRotFromVector(Vector2D a) {
    double angle = Math.atan2(a.y, a.x) + Math.PI/2;
    
    return new Rotation(0, 0, 1, angle);
  }
  
}
