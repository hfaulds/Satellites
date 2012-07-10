package actors;

public class PointLightActor {
  public final float[] ambientColour  = {(float) Math.random(), (float) Math.random(), (float) Math.random()};
  public final float[] specularColour = {(float) Math.random(), (float) Math.random(), (float) Math.random()};
  public final float[] lightPos       = {0, 0, 20, 1};
}
