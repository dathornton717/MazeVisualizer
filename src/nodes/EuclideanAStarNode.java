package nodes;

// Represents a node to be used when using A* search with Euclidean distance heuristic
public class EuclideanAStarNode extends Node implements Comparable<EuclideanAStarNode> {
  public EuclideanAStarNode(int row, int col, boolean found) {
    super(row, col, found);
  }

  public EuclideanAStarNode(int row, int col, boolean top, boolean right, boolean bottom, boolean left) {
    super(row, col, top, right, bottom, left);
  }

  @Override
  public int compareTo(EuclideanAStarNode o) {
    return (int)((this.distance + this.euclideanDistance()) -
            (o.distance + o.euclideanDistance()));
  }
}
