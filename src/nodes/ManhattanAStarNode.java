package nodes;

// Represents a node used in A* Search with manhattan distance heuristic
public class ManhattanAStarNode extends Node implements Comparable<ManhattanAStarNode> {

  public ManhattanAStarNode(int row, int col, boolean found) {
    super(row, col, found);
  }

  public ManhattanAStarNode(int row, int col, boolean top, boolean right, boolean bottom, boolean left) {
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
  public int compareTo(ManhattanAStarNode o) {
    return (this.distance + this.manhattanDistance()) -
            (o.distance + o.manhattanDistance());
  }
}
