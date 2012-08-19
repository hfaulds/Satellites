

import gui.GUI;
import gui.GUIEventListener;
import gui.LoginGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Main {
  
  private JFrame frame = new JFrame();
  private GUI gui = new LoginGUI();
  
  public Main() {
    this.switchGUI(new LoginGUI());
    

    frame.add(gui);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        close();
      }
    });
    
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  
  protected void switchGUI(final GUI other) {
    this.gui.close();
    frame.remove(this.gui);
    
    this.gui = other;
    frame.add(gui);
    gui.addGuiChangeListener(new GUIEventListener() {
      @Override
      public void change(GUI g) {
        switchGUI(g);
      }

      @Override
      public void exit() {
        close();
      }
    });
    
    SwingUtilities.invokeLater(new Runnable() {
      @Override
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
