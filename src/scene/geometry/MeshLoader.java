package scene.geometry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import math.Vector3D;

public class MeshLoader {

  private final static String FOLDER_PATH = "assets/meshes/";

  public static Mesh loadOBJ(String filename) throws FileNotFoundException {
    String fullpath = FOLDER_PATH + filename;
    return loadOBJ(new File(fullpath));
  }

  public static Mesh loadOBJ(File file) throws FileNotFoundException {
    return loadOBJ(new BufferedReader(new FileReader(file)), file.getName());
  }

  private static Mesh loadOBJ(BufferedReader file, String filename)
      throws FileNotFoundException {
    Vector<Vector3D> vertices = new Vector<Vector3D>();
    Vector<Triangle> triangles = new Vector<Triangle>();
    Vector<Vector3D> uvwCoords = new Vector<Vector3D>();

    try {
      for(String line = file.readLine(); line != null; line = file.readLine()) {
        StringTokenizer st = new StringTokenizer(line, "\t ");

        if (st.hasMoreTokens()) {
          
          String data = st.nextToken();
          
          if (data.equalsIgnoreCase("v")) {
            
            /**New Vertex**/
            vertices.add(new Vector3D(
                nextDouble(st), 
                nextDouble(st),
                nextDouble(st)
                ));
            
          } else if (data.equalsIgnoreCase("vn")) {
            
            /**New Normal**/
            throw new RuntimeException("TODO");
            
          } else if (data.equalsIgnoreCase("vt")) {
            
            /**New Texture Coordinate**/
            uvwCoords.add(new Vector3D(
                nextDouble(st), 
                nextDouble(st), 
                nextDouble(st)
                ));
            
          } else if (data.equalsIgnoreCase("f")) {
            
            /**New Triangle**/
            Vector3D[] localverts = new Vector3D[3];
            Vector3D[] localuvws = new Vector3D[3];
            
            for(int i=0; i < 3; i++)
            {
              StringTokenizer subTokenizer = new StringTokenizer(st.nextToken(), "/");
              
              localverts[i] = vertices.elementAt(nextInt(subTokenizer));
              localuvws[i] = uvwCoords.get(nextInt(subTokenizer));
            }
            
            triangles.add(new Triangle(localverts, localuvws));
          }
        }
      }

      file.close();

      return new Mesh(vertices.toArray(new Vector3D[0]), triangles.toArray(new Triangle[0]))._smoothNormals();

    } catch (IOException e) {
      System.out.println(e);
      return null;
    }

  }

  private static int nextInt(StringTokenizer tokenizer) {
    return Integer.parseInt(tokenizer.nextToken()) - 1;
  }

  private static double nextDouble(StringTokenizer st) {
    return Double.parseDouble(st.nextToken());
  }
}