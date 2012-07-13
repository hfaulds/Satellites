package scene.material;

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
  public BufferedImage rawTexture;
  
  public Material() {
    ambient = new Colour ( 0.2, 0.2, 0.2, 1 );
    diffuse = new Colour ( 0.8, 0.8, 0.8, 1 );
    specular = new Colour ( 0.5, 0.5, 0.5, 1 );
    emission = new Colour ( 0, 0, 0, 1 );
    shininess = 32;
  }
  
  public Material(BufferedImage rawTexture) {
    this.rawTexture = rawTexture;
    this.ambient = new Colour ( 0.2, 0.2, 0.2, 1 );
    this.diffuse = new Colour ( 0.8, 0.8, 0.8, 1 );
    this.specular = new Colour ( 0.5, 0.5, 0.5, 1 );
    this.emission = new Colour ( 0, 0, 0, 1 );
    this.shininess = 32;
  }

  public void init(GL2 gl)
  {
    texture = AWTTextureIO.newTexture(GLProfile.getDefault(), rawTexture, false);
  }
  
  public void startRender(GL2 gl) {
    texture.enable(gl);
    texture.bind(gl);

    gl.glColor3d(1, 1, 1);
    
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT,
        ambient.toFloat(), 0);
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE,
        diffuse.toFloat(), 0);
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR,
       specular.toFloat(), 0);
    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION,
        emission.toFloat(), 0);
    gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS,
        shininess);
    
    texture.setTexParameteri(gl, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
  }
  
  public void stopRender(GL2 gl) {
    texture.disable(gl);
  }

}