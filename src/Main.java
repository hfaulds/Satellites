import gui.GUI;
import gui.GUIChangeListener;
import gui.GUIEvent;
import gui.PreGameGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Main {

  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  
  private JFrame frame = new JFrame();
  private GUI gui = new PreGameGUI();
  
  public Main() {
    frame.setSize(WIDTH , HEIGHT);
    frame.setVisible(true);
    
    gui.addGuiChangeListener(new GUIChangeListener() {
      @Override
      public void changedGUI(GUIEvent e) {
        switchGUI(e.gui);
      }
    });
    
    frame.add(gui);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        gui.close();
        frame.dispose();
        System.exit(0);
      }
    });
  }
  
  protected void switchGUI(GUI other) {
    gui.close();
    frame.remove(gui);
    
    gui = other;
    frame.add(gui);
    
    frame.revalidate();
  }

  public static void main(String ... args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main();
      }
    });
  }
}
