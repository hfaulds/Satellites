import gui.GUI;
import gui.PreGameGUI;
import gui.event.GUIEvent;
import gui.event.GUIEventListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Main {
  
  private JFrame frame = new JFrame();
  private GUI gui = new PreGameGUI();
  
  public Main() {
    this.switchGUI(new PreGameGUI());
    
    gui.addGuiChangeListener(new GUIEventListener() {
      @Override
      public void change(GUIEvent e) {
        switchGUI(e.gui);
      }

      @Override
      public void exit() {
        close();
      }
    });
    
    frame.add(gui);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        close();
      }
    });
    
    frame.setVisible(true);
  }
  
  protected void switchGUI(GUI other) {
    gui.close();
    frame.remove(gui);
    
    gui = other;
    frame.add(gui);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        frame.setSize(gui.getWidth() , gui.getHeight());
        frame.revalidate();
        gui.init();
      }
    });
  }

  private void close() {
    gui.close();
    frame.dispose();
    System.exit(0);
  }

  public static void main(String ... args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main();
      }
    });
  }
}
