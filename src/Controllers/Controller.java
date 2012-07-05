package Controllers;

import Actors.Actor;

public abstract class Controller<ActorType extends Actor> {

  public final ActorType actor;
  
  public Controller(ActorType actor) {
    this.actor = actor;
  }

  public abstract void tick(Actor[] actors);
}
