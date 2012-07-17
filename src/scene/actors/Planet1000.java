package scene.actors;

import scene.graphics.Graphic;
import scene.graphics.Planet1000Graphic;
import geometry.Rotation;
import geometry.Vector2D;

public class Planet1000 extends SatelliteActor {

  public static final int MASS = 10;
  private static Graphic GRAPHIC = new Planet1000Graphic();  
  
  public Planet1000(double x, double y) {
    super(new Vector2D(x,y), new Rotation(), MASS, GRAPHIC);
  }

}
