package gui.dialog.component;

import gui.dialog.SelectServerDialog;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SelectServerListener implements
    DocumentListener {
  
  private final SelectServerDialog dialog;

  public SelectServerListener(SelectServerDialog dialog) {
    this.dialog = dialog;
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    dialog.refreshConnectButton();
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    dialog.refreshConnectButton();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    dialog.refreshConnectButton();
  }
}