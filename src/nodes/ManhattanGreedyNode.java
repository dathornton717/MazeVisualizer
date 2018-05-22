package nodes;

// Represents a node to be used in greedy best first search with
// manhattan distance heuristic
public class ManhattanGreedyNode extends Node implements Comparable<ManhattanGreedyNode> {

  public ManhattanGreedyNode(int row, int col, boolean found) {
    super(row, col, found);
  }

  public ManhattanGreedyNode(int row, int col, boolean top, boolean right, boolean bottom, boolean left) {
    super(row, col, top, right, bottom, left);
    this.row = row;
    this.col = col;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
    this.found = false;
    this.distance = 0;
  }

  @Override
  public int compareTo(ManhattanGreedyNode o) {
    return this.manhattanDistance() - o.manhattanDistance();
//    return (this.distance + this.manhattanDistance()) -
//            (o.distance + o.manhattanDistance());
  }
}
