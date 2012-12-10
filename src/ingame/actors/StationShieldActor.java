package ingame.actors;

import core.Actor;
import core.ActorInfo;

public class StationShieldActor extends Actor {

  private static final double MASS = 0;
  private static final String MESH = "Station-Mk2-Shield.obj";
  
  public final StationActor station;
  
  protected StationShieldActor(StationActor station) {
    super(new ActorInfo(station.position, station.rotation, MASS, MESH));
    this.station = station;
  }
  
}