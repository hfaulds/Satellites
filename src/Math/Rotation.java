package Math;

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
  
  public void _add(double n) {
    this.mag += n;
  }

  public double degrees() {
    return this.mag * 180/Math.PI;
  }
  
}
