package core;

import java.util.List;

public interface Controller {
  public void tick(long dt, List<Actor> actors);
  public void destroy();
}
