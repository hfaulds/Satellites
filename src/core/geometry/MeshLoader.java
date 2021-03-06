package core.geometry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class MeshLoader {

  private final static String FOLDER_PATH = "assets/";

  public static Mesh loadOBJ(String filename) throws FileNotFoundException {
    String fullpath = FOLDER_PATH + filename;
    if (filename == "")
      return new Mesh();
    else
      return loadOBJ(new File(fullpath));
  }

  public static Mesh loadOBJ(File file) throws FileNotFoundException {
    return loadOBJ(new BufferedReader(new FileReader(file)), file.getName());
  }

  public static Mesh loadMesh(String file) {
    try {
      return MeshLoader.loadOBJ(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static Mesh loadOBJ(BufferedReader file, String filename)
      throws FileNotFoundException {
    Vector<Vector3D> vertices = new Vector<Vector3D>();
    Vector<Vector3D> uvws = new Vector<Vector3D>();
    Vector<Vector3D> normals = new Vector<Vector3D>();
    Vector<Triangle> triangles = new Vector<Triangle>();

    try {
      for (String line = file.readLine(); line != null; line = file.readLine()) {
        StringTokenizer st = new StringTokenizer(line, "\t ");

        if (st.hasMoreTokens()) {

          String data = st.nextToken();

          if (data.equalsIgnoreCase("v")) {

            /** New Vertex **/
            vertices.add(nextVector(st));

          } else if (data.equalsIgnoreCase("vn")) {

            /** New Normal **/
            normals.add(nextVector(st));

          } else if (data.equalsIgnoreCase("vt")) {

            /** New Texture Coordinate **/
            uvws.add(nextVector(st));

          } else if (data.equalsIgnoreCase("f")) {

            /** New Triangle **/
            Vector3D[] localverts = new Vector3D[3];
            Vector3D[] localnormals = new Vector3D[3];
            Vector3D[] localuvws = new Vector3D[3];

            for (int i = 0; i < 3; i++) {
              String nextToken = st.nextToken();
              StringTokenizer subTokenizer = new StringTokenizer(nextToken, "/");

              localverts[i] = vertices.elementAt(nextInt(subTokenizer));
              localuvws[i] = uvws.get(nextInt(subTokenizer));
              localnormals[i] = normals.elementAt(nextInt(subTokenizer));
            }

            triangles.add(new Triangle(localverts, localnormals, localuvws));
          }
        }
      }

      file.close();

      Vector3D[] vertexArray = vertices.toArray(new Vector3D[0]);
      Triangle[] triangleArray = triangles.toArray(new Triangle[0]);

      return new Mesh(vertexArray, triangleArray, filename);

    } catch (IOException e) {
      System.out.println(e);
      return null;
    }

  }

  private static Vector3D nextVector(StringTokenizer st) {
    return new Vector3D(nextDouble(st), nextDouble(st), nextDouble(st));
  }

  private static int nextInt(StringTokenizer tokenizer) {
    return Integer.parseInt(tokenizer.nextToken()) - 1;
  }

  private static double nextDouble(StringTokenizer st) {
    return Double.parseDouble(st.nextToken());
  }
}