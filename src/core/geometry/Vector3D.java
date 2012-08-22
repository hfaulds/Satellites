package core.geometry;

import core.render.material.Colour;

public class Vector3D {
  
  public Colour colour = new Colour();
  public int renderSize = 5;
  
  public double x, y, z;

  public Vector3D() {
    this(0,0,0);
  }

  public Vector3D(Vector3D vector) {
    this(vector.x, vector.y, vector.z);
  }
  
  public Vector3D(Vector2D vector) {
    this(vector.x, vector.y, 0);
  }
  
  public Vector3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  

  public void _set(Vector3D other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
  }

  public double distanceTo(Vector3D vert) {
    double x = Math.pow(vert.x - this.x, 2);
    double y = Math.pow(vert.y - this.y, 2);
    double z = Math.pow(vert.z - this.z, 2);
    return Math.sqrt(x + y + z);
  }

  public Vector3D add(Vector3D vert) {
    double x = this.x + vert.x;
    double y = this.y + vert.y;
    double z = this.z + vert.z;
    return new Vector3D(x, y, z);
  }

  public Vector3D subtract(Vector3D vert) {
    double x = this.x - vert.x;
    double y = this.y - vert.y;
    double z = this.z - vert.z;
    return new Vector3D(x, y, z);
  }

  public Vector3D multiply(double n) {
    double x = this.x * n;
    double y = this.y * n;
    double z = this.z * n;
    return new Vector3D(x,y,z);
  }

  public Vector3D divide(double n) {
    if(n != 0) {
      double x = this.x / n;
      double y = this.y / n;
      double z = this.z / n;
      return new Vector3D(x,y,z);
    }
    return new Vector3D(this);
  }

  public Vector3D crossProduct(Vector3D vert) {
    double x = this.y * vert.z - this.z * vert.y;
    double y = this.z * vert.x - this.x * vert.z;
    double z = this.x * vert.y - this.y * vert.x;
    return new Vector3D(x, y, z);
  }

  public double dotProduct(Vector3D vert) {
    double x = this.x * vert.x;
    double y = this.y * vert.y;
    double z = this.z * vert.z;
    return (x + y + z);
  }
  
  public double magnitude() {
    double a = Math.pow(this.x, 2);
    double b = Math.pow(this.y, 2);
    double c = Math.pow(this.z, 2);
    return Math.sqrt(a + b + c);
  }

  public Vector3D normalized() {
    if(this.magnitude() != 0)
      return this.divide(this.magnitude());
    else
      return new Vector3D(this);
  }

  public Vector3D _normalize() {
    double mag = this.magnitude();
    this.x /= mag;
    this.y /= mag;
    this.z /= mag;
    return this;
  }
  
  public void _add(Vector3D vector) {
    this.x += vector.x;
    this.y += vector.y;
    this.z += vector.z;
  }
  
  public double[] toDouble() {
    return new double[]{x,y,z};
  }
  
  public float[] toFloat() {
    return new float[]{(float) x,(float) y,(float) z};
  }

  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }

}
