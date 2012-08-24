package ingame.controllers;

import java.util.List;

import core.Actor;

public interface Controller {
  public void tick(long dt, List<Actor> actors);
  public void destroy();
}
