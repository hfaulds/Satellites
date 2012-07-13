package gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class GUI extends JPanel {

  private List<GUIChangeListener> listeners = new LinkedList<GUIChangeListener>();
  
  public void addGuiChangeListener(GUIChangeListener listener) {
    listeners.add(listener);
  }
  
  protected void fireEvent(GUIEvent e) {
    for(GUIChangeListener listener : listeners) {
      listener.changedGUI(e);
    }
  }
  
  public abstract int getWidth();
  public abstract int getHeight();

  public void init() {};
  public void close() {};
}
