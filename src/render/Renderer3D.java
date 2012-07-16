package render;

import geometry.Vector2D;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

import scene.Scene;
import scene.actors.Actor;
import scene.actors.PointLightActor;



public class Renderer3D {
  
  private static final GLU glu = new GLU();
  
  private boolean bUpdateMatrices          = true;
  private static double[] modelMatrix      = new double[16];
  private static double[] projectionMatrix = new double[16];
  private static int[] viewportMatrix      = new int[4];

  public void init(GL2 gl, Scene scene) {
    gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClearDepth(1.0f);
    
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LEQUAL);
    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    
    for(int i=0; i < scene.lights.length; i++) {
      PointLightActor light = scene.lights[i];
      int lightID = GL2.GL_LIGHT0 + i;
      gl.glLightfv(lightID, GL2.GL_DIFFUSE, light.diffuseColour, 0);
      gl.glLightfv(lightID, GL2.GL_POSITION, light.lightPos, 0);
      gl.glLightfv(lightID, GL2.GL_SPECULAR, light.specularColour, 0);
      gl.glEnable(lightID);
    }
    
    initActors(gl, scene);
    gl.glEnable(GL2.GL_LIGHTING);
  }

  public void clear(GL2 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
  }
  
  public void preRender(final GL2 gl, Vector2D camera, double ratio, double zoom) {
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluPerspective(45, ratio, 1, 1000);
    glu.gluLookAt(camera.x, camera.y, zoom, camera.x, camera.y, 0, 0, 1, 0);

    if(bUpdateMatrices)
      updateMatrices(gl);
    
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glEnable(GL.GL_MULTISAMPLE);
  }

  private void updateMatrices(final GL2 gl) {
      gl.glGetIntegerv(GL2.GL_VIEWPORT, viewportMatrix, 0);
      gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelMatrix, 0);
      gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projectionMatrix, 0);
      bUpdateMatrices = false;
  }
  
  public void updateMatrices() {
    bUpdateMatrices = true;
  }
  
  public static Vector2D project(Vector2D position) {
    double[] player = new double[4];
    
    glu.gluProject(position.x, position.y, Vector2D.Z, 
        modelMatrix, 0,
        projectionMatrix, 0, 
        viewportMatrix, 0,
        player, 0);
    
    return new Vector2D(player[0], viewportMatrix[3] - player[1]);
  }
  
  public void render(GL2 gl, Scene scene) {
    initActors(gl, scene);
    renderActors(gl, scene);
  }

  private void renderActors(GL2 gl, Scene scene) {
    synchronized(scene.actors) {
      for(Actor actor : scene.actors) {
        gl.glPushMatrix();
          actor.render(gl, glu);
        gl.glPopMatrix();
      }
    }
  }

  private void initActors(GL2 gl, Scene scene) {
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
}