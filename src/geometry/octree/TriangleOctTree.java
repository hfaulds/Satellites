package geometry.octree;

import geometry.Box;
import geometry.Triangle;

import java.util.Vector;

public class TriangleOctTree {

  public static final int MAX_ELEMENTS = 5;

  public final Box space;
  
  public final TriangleOctTree[] subTrees;
  public final Vector<Triangle> elements;
  
  public TriangleOctTree(Box space, TriangleOctTree[] subTrees) {
    this(space, subTrees, new Vector<Triangle>());
  }

  public TriangleOctTree(Box space, Vector<Triangle> elements) {
    this(space, new TriangleOctTree[0], elements);
  }

  public TriangleOctTree(Box space, TriangleOctTree[] subTrees, Vector<Triangle> elements) {
    this.space = space;
    this.subTrees = subTrees;
    this.elements = elements;
  }
  
  public boolean collide(Box space) {
    return Box.boxesIntersect(this.space, space);
  }
}
