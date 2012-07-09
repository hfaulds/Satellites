package controllers;

import actors.Actor;

public abstract class Controller {

  public final Actor actor;
  
  public Controller(Actor actor) {
    this.actor = actor;
  }

  public abstract void tick(Actor[] actors);
}
