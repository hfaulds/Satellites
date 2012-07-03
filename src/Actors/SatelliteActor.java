package Actors;

import Graphics.SatelliteGraphic;

public class SatelliteActor extends Actor {
  
  public static final double G = 0.0001;
  
  public final static double density = 50;
  
  public double vx;
  public double vy;

  private double radius;
  
  public SatelliteActor(double x, double y) {
    this(x, y, 0.1);
  }
  
  public SatelliteActor(double x, double y, double mass) {
    this(x, y, 0, 0, mass);
  }
  public SatelliteActor(double x, double y, double vx, double vy, double mass) {
    super(x, y, mass);
    
    this.vx = vx;
    this.vy = vy;

    this.radius = calcRadius(mass);
    this.graphic = new SatelliteGraphic(radius);
  }

  private static double calcRadius(double mass) {
    double volume = mass / density;
    return Math.pow( (3.0 * volume) / (4.0 * Math.PI) , (1.0 / 3.0) );
  }
  
  public void updateVelocity(Actor[] actors) {
    double ax = 0;
    double ay = 0;
  
    for(Actor actor : actors) {
      if(actor != this)
      {
        /*Distance Vector Components*/
        double dx = actor.x - this.x;
        double dy = actor.y - this.y;
        
        /*Distance magnitude*/
        double d = Math.sqrt(dx*dx + dy*dy);

        if(d > radius*2) {
          /*Normalised Distance Vectors*/
          dx = dx/d;
          dy = dy/d;
          
          /*Force Magnitude*/
          double f = G * this.mass * actor.mass / Math.pow(d, 2);
          
          /*Force Vectors*/
          double fx = dx * f;
          double fy = dy * f;
          
          /*Acceleration Vectors*/
          ax += fx / this.mass;
          ay += fy / this.mass;
        }else{
          /*Collision*/
          this.vx *= 0.9;
          this.vy *= 0.9;
          // V = [dx dy]
          // N = 
          //Vnew = b * ( -2*(V dot N)*N + V )
        }
      }
    }

    /*Acceleration*/
    this.vx += ax;
    this.vy += ay;
  }
  
  public void updatePosition() {
    this.x += vx;
    this.y += vy;
  }
}
