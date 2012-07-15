package geometry;

public class Rotation {

  public double x;
  public double y;
  public double z;
  
  public double mag;
  
  public Rotation() {
    this.x = 0;
    this.y = 0;
    this.z = 1;
    this.mag = 0;
  }
  
  public Rotation(Rotation other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    this.mag = other.mag;
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
  
}
