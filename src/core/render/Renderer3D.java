package core.render;

import ingame.actors.PointLightActor;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

import core.Actor;
import core.Scene;
import core.geometry.Vector2D;


public class Renderer3D {
  
  private static final GLU glu = new GLU();
  
  private final Scene scene;
  
  private static double[] modelMatrix      = new double[16];
  private static double[] projectionMatrix = new double[16];
  private static int[] viewportMatrix      = new int[4];

  public Renderer3D(Scene scene) {
    this.scene = scene;
  }

  public void init(GL2 gl) {
    gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClearDepth(1.0f);
    
    gl.glEnable(GL2.GL_CULL_FACE);
    gl.glCullFace(GL2.GL_BACK);
    
    gl.glShadeModel(GL2.GL_SMOOTH);
    gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    
    gl.glEnable(GL2.GL_BLEND); 
    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

    gl.glEnable(GL2.GL_NORMALIZE);
    
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LEQUAL);
    
    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    
    for(int i=0; i < scene.lights.length; i++) {
      PointLightActor light = scene.lights[i];
      int lightID = GL2.GL_LIGHT0 + i;
      gl.glLightfv(lightID, GL2.GL_DIFFUSE, light.diffuseColour, 0);
      gl.glLightfv(lightID, GL2.GL_SPECULAR, light.specularColour, 0);
      gl.glLightfv(lightID, GL2.GL_POSITION, light.position, 0);
      gl.glEnable(lightID);
    }
    
    initNewActors(gl);
    gl.glEnable(GL2.GL_LIGHTING);
  }

  private void renderActors(GL2 gl) {
    gl.glEnable(GL2.GL_NORMALIZE);
    synchronized(scene.actors) {
      for(Actor actor : scene.actors) {
        gl.glPushMatrix();
          actor.render(gl, glu);
        gl.glPopMatrix();
      }
    }
  }

  private void initNewActors(GL2 gl) {
    synchronized(scene.actors) {
      while(scene.actorqueue.size() > 0) {
        gl.glPushMatrix();
          Actor actor = scene.actorqueue.poll();
          actor.init(gl, glu);
          scene.actors.add(actor);
        gl.glPopMatrix();
      }
    }
  }
  
  public void render(GL2 gl, Camera camera) {
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    
    glu.gluPerspective(45, camera.ratio, 1, 1000);
    
    Vector2D position = camera.getPosition();
    glu.gluLookAt(position.x, 
                  position.y, 
                  camera.zoom, 
                  position.x, 
                  position.y, 
                  0, 0, 1, 0);

    if(camera.bZoom) {
      updateMatrices(gl);
      camera.bZoom = false;
    }
    
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    
    gl.glEnable(GL.GL_MULTISAMPLE);
    
    initNewActors(gl);
    renderActors(gl);
  }


  private void updateMatrices(final GL2 gl) {
    gl.glGetIntegerv(GL2.GL_VIEWPORT, viewportMatrix, 0);
    gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelMatrix, 0);
    gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projectionMatrix, 0);
  }
  
  public static Vector2D project(Vector2D position) {
    double[] player = new double[4];
    
    glu.gluProject(position.x, position.y, Vector2D.Z, 
        modelMatrix, 0,
        projectionMatrix, 0, 
        viewportMatrix, 0,
        player, 0);
    
    return new Vector2D(player[0], player[1]);
  }
  
}
