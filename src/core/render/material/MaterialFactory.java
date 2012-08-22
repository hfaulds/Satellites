package core.render.material;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.util.awt.ImageUtil;

public class MaterialFactory {

  private final static String TEXTURE_PATH = "assets/textures/";

  public static Material createMaterial(String filename) throws IOException {
    return createMaterial(new File(TEXTURE_PATH + filename));
  }

  public static Material createMaterial(File file) throws IOException {
    BufferedImage img = ImageIO.read(file);
    ImageUtil.flipImageVertically(img);
    return new Material(img);
  }
}
