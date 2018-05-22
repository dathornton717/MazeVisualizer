package nodes;

// Represents a node to be used in greedy best first search
// with Euclidean Distance heuristic
public class EuclideanGreedyNode extends Node implements Comparable<EuclideanGreedyNode> {
  public EuclideanGreedyNode(int row, int col, boolean found) {
    super(row, col, found);
  }

  public EuclideanGreedyNode(int row, int col, boolean top, boolean right, boolean bottom, boolean left) {
    super(row, col, top, right, bottom, left);
  }

  @Override
  public int compareTo(EuclideanGreedyNode o) {
    return (int) (this.euclideanDistance() - o.euclideanDistance());
  }
}
