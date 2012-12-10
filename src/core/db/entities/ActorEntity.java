package core.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import core.Actor;
import core.ActorInfo;
import core.geometry.Rotation;
import core.geometry.Vector2D;

@Entity
@Table(name = "ACTORS")
public class ActorEntity {
  
  @SuppressWarnings("unused")
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int id;
  
  @Column(name = "class")
  public Class<? extends Actor> actorClass;

  @Column(name = "xPos")
  public double xPos;
  @Column(name = "yPos")
  public double yPos;

  @Column(name = "xRot")
  public double xRot;
  @Column(name = "yRot")
  public double yRot;
  @Column(name = "zRot")
  public double zRot;
  @Column(name = "magRot")
  public double magRot;

  @Column(name = "mass")
  private int mass;
  
  @Column(name = "mesh")
  private String mesh;

  public ActorEntity(){
   
  }
  
  public ActorEntity(Class<? extends Actor> actorClass, Vector2D position, Rotation rotation, int mass, String mesh) {
    this.actorClass = actorClass;
    this.xPos = position.x;
    this.yPos = position.y;
    this.xRot = rotation.x;
    this.yRot = rotation.y;
    this.zRot = rotation.z;
    this.magRot = rotation.mag;
    this.mass = mass;
    this.mesh = mesh;
  }

  public Actor toActor() {
    return Actor.fromInfo(actorClass, new Vector2D(xPos, yPos), new Rotation(xRot, yRot, zRot, magRot), (double)mass , ActorInfo.NEXT_ID(), mesh);
  }
  
}
