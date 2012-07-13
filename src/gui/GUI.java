package gui;

import gui.event.GUIEvent;
import gui.event.GUIEventListener;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class GUI extends JPanel {

  private List<GUIEventListener> listeners = new LinkedList<GUIEventListener>();
  
  public void addGuiChangeListener(GUIEventListener listener) {
    listeners.add(listener);
  }
  
  protected void switchGUI(GUIEvent e) {
    for(GUIEventListener listener : listeners) {
      listener.change(e);
    }
  }
  
  protected void exit() {
    for(GUIEventListener listener : listeners) {
      listener.exit();
    }
  }
  
  public abstract int getWidth();
  public abstract int getHeight();

  public void init() {};
  public void close() {};
}
