package scene.actors;

import geometry.Vector3D;

public class PointLightActor {
  
  public final float[] diffuseColour  = {1.0f, 1.0f, 1.0f};
  public final float[] specularColour = {1.0f, 1.0f, 1.0f};
  public final float[] position;
 
  public PointLightActor() {
    this(new Vector3D(0,0,0));
  }
  
  public PointLightActor(Vector3D position) {
    this.position = position.toFloat();
  }
}