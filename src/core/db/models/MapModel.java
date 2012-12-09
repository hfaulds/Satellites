package core.db.models;

import org.hibernate.Session;

import core.db.entities.MapEntity;

public class MapModel {

  public static MapEntity findByName(String name, Session session) {
    session.beginTransaction();
    MapEntity map = (MapEntity)session.get(MapEntity.class, name);
    session.getTransaction().commit();
    return map;
  }
}
