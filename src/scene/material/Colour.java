package scene.material;

public class Colour {
  public final double r;
  public final double g;
  public final double b;
  public final double a;

  public Colour(double r, double g, double b, double a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }
  
  public Colour(double r, double g, double b) {
    this(r, g, b, 1);
  }
  
  public Colour(Colour colour)
  {
    this(colour.r, colour.g, colour.b, colour.a);
  }
  
  public Colour() {
    this(1, 1, 1, 1);
  }

  public float[] toFloat() {
    
    return new float[] { 
        (float) r, 
        (float) g, 
        (float) b, 
        (float) a 
        };
  }
}
