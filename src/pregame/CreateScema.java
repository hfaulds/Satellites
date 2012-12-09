package pregame;

import ingame.actors.Planet001Actor;
import ingame.actors.ShipActor;
import ingame.actors.StationActor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import core.db.entities.ActorEntity;
import core.db.entities.MapEntity;
import core.db.entities.UserEntity;
import core.geometry.Rotation;
import core.geometry.Vector2D;

public class CreateScema {

  public static void main(String ... args) {
    Configuration configuration = new Configuration().configure();  
    configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    ActorEntity[] actors = new ActorEntity[] {
        new ActorEntity(Planet001Actor.class, new Vector2D(50,50), new Rotation(), Planet001Actor.MASS),
        new ActorEntity(StationActor.class, new Vector2D(-35, 17), new Rotation(), StationActor.MASS)
    };
    
    session.save(actors[0]);
    session.save(actors[1]);
    
    session.save(new MapEntity("default", actors));
    

    ActorEntity playerActor = new ActorEntity(ShipActor.class, new Vector2D(), new Rotation(), ShipActor.MASS);
    session.save(playerActor);
    session.save(new UserEntity("test", "test", playerActor));
    

    ActorEntity playerActor2 = new ActorEntity(ShipActor.class, new Vector2D(0,20), new Rotation(), ShipActor.MASS);
    session.save(playerActor2);
    session.save(new UserEntity("test2", "test", playerActor2));
    
    session.getTransaction().commit();
    session.close();
  }
}
