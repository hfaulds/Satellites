package core.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import core.Scene;

@Entity
@Table(name = "MAPS")
public class MapEntity {
  
  @Id
  @Column(name = "name")
  public String name;
  
  @OneToMany()
  @OrderColumn(name = "id")
  public ActorEntity[] actors;
  
  public MapEntity() {
    this("", new ActorEntity[0]);
  }
  
  public MapEntity(String name, ActorEntity[] actors) {
    this.name = name;
    this.actors = actors;
  }
  
  public Scene toScene() {
    Scene scene = new Scene();
    for(ActorEntity actor : actors)
      scene.forceAddActor(actor.toActor());
    return scene;
  }
}
