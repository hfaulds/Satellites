package scene.controllers;

import java.util.List;

import scene.actors.Actor;


public interface Controller {
  public void tick(List<Actor> actors);
}
