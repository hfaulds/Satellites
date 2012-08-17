package render.material;

import java.awt.image.BufferedImage;

import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class Material implements Cloneable {
  
  public Colour ambient;
  public Colour diffuse;
  public Colour specular;
  public Colour emission;
  public float shininess;
  
  public Texture texture;
  private BufferedImage rawTexture;
  
  public Material() {
    this.ambient = new Colour(0.2, 0.2, 0.2);
    this.diffuse = new Colour(0.8, 0.8, 0.8);
    this.specular = new Colour(0.5, 0.5, 0.5);
    this.emission = new Colour(0.1, 0.1, 0.1);
    this.shininess = 100;
  }
  
  public Material(BufferedImage rawTexture) {
    this();
    this.rawTexture = rawTexture;
  }

  public void init(GL2 gl)
  {
    texture = AWTTextureIO.newTexture(GLProfile.getDefault(), rawTexture, false);
  }
  
  public void startRender(GL2 gl) {
    texture.enable(gl);
    texture.bind(gl);

    gl.glColor3d(1, 1, 1);
    
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ambient.toFloat(), 0);
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, diffuse.toFloat(), 0);
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, specular.toFloat(), 0);
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, emission.toFloat(), 0);
    gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess);
    
    texture.setTexParameteri(gl, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
  }
  
  public void stopRender(GL2 gl) {
    texture.disable(gl);
  }

}