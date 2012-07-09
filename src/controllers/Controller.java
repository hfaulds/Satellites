package controllers;

import java.util.List;

import actors.Actor;

public interface Controller {
  public void tick(List<Actor> actors);
}
