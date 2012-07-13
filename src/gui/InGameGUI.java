package gui;

import gui.components.sprites.MsgSprite;

import java.awt.BorderLayout;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import net.NetworkConnection;
import net.msg.ChatMsg;
import scene.Scene;
import scene.SceneUpdater;
import scene.render.SceneRenderer;

import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class InGameGUI extends GUI implements GLEventListener {
  
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;

  private final NetworkConnection connection;
  private final Scene scene;
  
  private final SceneRenderer renderer;
  private final SceneUpdater updater;
  
  private final GLWindow glWindow;
  private final NewtCanvasAWT canvas;
  private final FPSAnimator animator;

  public InGameGUI(Scene scene, NetworkConnection connection) {
    this.scene = scene;
    this.renderer = new SceneRenderer(scene);
    this.updater = new SceneUpdater(scene);
    this.connection = connection;
    
    this.glWindow = createGLWindow(createCapabilities(), scene);
    this.canvas = createCanvas(glWindow);
    this.animator = new FPSAnimator(glWindow, 20);
    
    this.setLayout(new BorderLayout());
    this.add(canvas, BorderLayout.CENTER);
    this.setupChat();
  }

  @Override
  public void init() {
    glWindow.requestFocus();
    animator.start();
  }

  private void setupChat() {
    glWindow.addKeyListener(new KeyListener() {
      @Override
      public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        MsgSprite messageHandler = scene.messageHandler;
        
        if(messageHandler.inputting) {
          switch(keyCode) {
            case 10: // enter
              if(messageHandler.getInput().length() > 0) {
                ChatMsg msg = new ChatMsg(messageHandler.getInput());
                messageHandler.displayMessage(msg);
                connection.sendMessage(msg);
              }
              messageHandler.clearInput();
              break;
            case 27: // escape
              messageHandler.clearInput();
              messageHandler.inputting = false;
              break;
            case 8: // backspace
              messageHandler.backSpace();
              break;
            default:
              char character = (char)keyCode;
              if(!e.isShiftDown())
                character =  Character.toLowerCase(character);
              messageHandler.addChar(character);
          }
        } else {
          if(keyCode == 'T') {
            messageHandler.inputting = true;
          }
        }
      }

      @Override
      public void keyPressed(KeyEvent arg0) {}
      @Override
      public void keyTyped(KeyEvent arg0) {}

    });
    
  }

  private GLCapabilities createCapabilities() {
    GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
    capabilities.setSampleBuffers(true);
    capabilities.setNumSamples(4);
    return capabilities;
  }

  private GLWindow createGLWindow(GLCapabilities capabilities, Scene scene) {
    GLWindow window = GLWindow.create(capabilities);
    window.addGLEventListener(this);
    window.addKeyListener(scene.input);
    return window;
  }

  private NewtCanvasAWT createCanvas(GLWindow window) {
    NewtCanvasAWT canvas = new NewtCanvasAWT(window);
    canvas.addMouseListener(renderer);
    canvas.addMouseWheelListener(renderer);
    canvas.addMouseMotionListener(renderer);
    return canvas;
  }


  @Override
  public void init(GLAutoDrawable drawable) {
    renderer.init(drawable);
  }
  
  @Override
  public void display(GLAutoDrawable drawable) {
    updater.tick();
    renderer.render(drawable);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    updater.tick();
    renderer.render(drawable);
  }
  
  @Override
  public void dispose(GLAutoDrawable drawable) {}

  @Override
  public void close() {
    animator.stop();
    connection.disconnect();
  }

  @Override
  public int getWidth() {
    return WIDTH;
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }
}
